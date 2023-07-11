package gg.jte.generated.ondemand.com.stella.free.view.page.layout.header;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import com.stella.free.view.component.auth.LoginModalViewComponent;
public final class JteHeaderViewComponentGenerated {
	public static final String JTE_NAME = "com/stella/free/view/page/layout/header/HeaderViewComponent.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,4,4,4,4,4,4,4,4,4,4,4,42,42,54,81,81,83,83,86,86,96,102};
	private static final gg.jte.runtime.BinaryContent BINARY_CONTENT = gg.jte.runtime.BinaryContent.load(JteHeaderViewComponentGenerated.class, "JteHeaderViewComponentGenerated.bin", 1125,622,1096,109,173,107,11);
	private static final byte[] TEXT_PART_BINARY_0 = BINARY_CONTENT.get(0);
	private static final byte[] TEXT_PART_BINARY_1 = BINARY_CONTENT.get(1);
	private static final byte[] TEXT_PART_BINARY_2 = BINARY_CONTENT.get(2);
	private static final byte[] TEXT_PART_BINARY_3 = BINARY_CONTENT.get(3);
	private static final byte[] TEXT_PART_BINARY_4 = BINARY_CONTENT.get(4);
	private static final byte[] TEXT_PART_BINARY_5 = BINARY_CONTENT.get(5);
	private static final byte[] TEXT_PART_BINARY_6 = BINARY_CONTENT.get(6);
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, LoginModalViewComponent loginModalViewComponent) {
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_0);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_1);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_2);
		if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_3);
		} else {
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_4);
		}
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_5);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_6);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		LoginModalViewComponent loginModalViewComponent = (LoginModalViewComponent)params.get("loginModalViewComponent");
		render(jteOutput, jteHtmlInterceptor, loginModalViewComponent);
	}
}
