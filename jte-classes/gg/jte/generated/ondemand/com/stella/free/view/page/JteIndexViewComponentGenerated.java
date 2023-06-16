package gg.jte.generated.ondemand.com.stella.free.view.page;
import com.stella.free.view.page.layout.footer.FooterViewComponent;
import de.tschuehly.spring.viewcomponent.jte.ViewContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
public final class JteIndexViewComponentGenerated {
	public static final String JTE_NAME = "com/stella/free/view/page/IndexViewComponent.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,4,5,7,7,7,7,7,7,7,7,7,19,19,19,21,21,22,22,22,23,23,36};
	private static final gg.jte.runtime.BinaryContent BINARY_CONTENT = gg.jte.runtime.BinaryContent.load(JteIndexViewComponentGenerated.class, "JteIndexViewComponentGenerated.bin", 425,123,86,26,61);
	private static final byte[] TEXT_PART_BINARY_0 = BINARY_CONTENT.get(0);
	private static final byte[] TEXT_PART_BINARY_1 = BINARY_CONTENT.get(1);
	private static final byte[] TEXT_PART_BINARY_2 = BINARY_CONTENT.get(2);
	private static final byte[] TEXT_PART_BINARY_3 = BINARY_CONTENT.get(3);
	private static final byte[] TEXT_PART_BINARY_4 = BINARY_CONTENT.get(4);
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String hello, Authentication authentication) {
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_0);
		if (authentication instanceof AnonymousAuthenticationToken) {
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_1);
		} else {
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_2);
			jteOutput.setContext("button", null);
			jteOutput.writeUserContent(hello);
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_3);
		}
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_4);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String hello = (String)params.get("hello");
		Authentication authentication = (Authentication)params.get("authentication");
		render(jteOutput, jteHtmlInterceptor, hello, authentication);
	}
}
