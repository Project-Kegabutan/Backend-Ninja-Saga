package co.id.beninjasaga.controller;

import co.id.beninjasaga.amf.Amf0EnvelopeWriter;
import co.id.beninjasaga.amf.Amf0Input;
import co.id.beninjasaga.amf.AmfSerializerLikePHP;
import co.id.beninjasaga.amf.NSRemotingInput;
import co.id.beninjasaga.util.router.ServiceRouter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
public class AmfController {
  private static final String SUCCESS = "/onResult";
  private static final String FAILURE = "/onStatus";
  private static final String DEFAULT_SEQ = "/1";

  private final ServiceRouter router;

  public AmfController(ServiceRouter router) {
    this.router = router;
  }

  @PostMapping(
          value="/amf/",
          consumes={"application/x-amf", MediaType.APPLICATION_OCTET_STREAM_VALUE},
          produces="application/x-amf"
  )
  public void handle(@RequestBody byte[] body, HttpServletResponse resp) throws IOException {
    String target=null, reqResponseUri=null; Object args=null; boolean fromAmf0=false;

    int ver = (body.length>=2) ? (((body[0] & 0xFF) << 8) | (body[1] & 0xFF)) : -1;

    if (ver == 3) {                           // FLASH (AMF3) REQUEST
      NSRemotingInput ns = new NSRemotingInput(body);
      ns.read();
      target = ns.targetUri;
      reqResponseUri = ns.responseUri;
      args = ns.body;
      fromAmf0 = false;
    } else {
      try {
        Amf0Input.Packet p = new Amf0Input(body).readPacket();
        if (!p.messages.isEmpty()) {
          var m = p.messages.get(0);
          target = m.targetUri;
          reqResponseUri = m.responseUri;
          args = m.body;
          fromAmf0 = true;
        }
      } catch (Exception ignore) {}

      if (target == null) {                   // fallback ke NS/Flash
        NSRemotingInput ns = new NSRemotingInput(body);
        ns.read();
        target = ns.targetUri;
        reqResponseUri = ns.responseUri;
        args = ns.body;
        fromAmf0 = false;
      }
    }

    Object payload;
    try {
      payload = router.invokeFlexible(target, args);
    } catch (Exception e) {
      String seq = extractSeq(reqResponseUri);
      String t = seq + "/onStatus";
      byte[] out = fromAmf0
              ? Amf0EnvelopeWriter.writeResponseAmf0Body(t, Map.of("status","0","error",e.getMessage()))
              : new AmfSerializerLikePHP(new File("logs")).writeResponse(t, "", Map.of("status","0","error",e.getMessage()));
      resp.setContentType("application/x-amf");
      resp.getOutputStream().write(out);
      return;
    }

    String seq = extractSeq(reqResponseUri);
    String t = seq + "/onResult";
    byte[] out = fromAmf0
            ? Amf0EnvelopeWriter.writeResponseAmf0Body(t, payload)                          // PyAMF
            : new AmfSerializerLikePHP(new File("logs")).writeResponse(t, "", payload);     // Flash ( -1 + 0x11 )

    resp.setContentType("application/x-amf");
    resp.getOutputStream().write(out);
  }


  private String extractSeq(String responseUri){
    String seq = (responseUri != null && responseUri.startsWith("/")) ? responseUri : DEFAULT_SEQ;
    if (!seq.startsWith("/")) seq = "/" + seq;
    return seq;
  }
}
