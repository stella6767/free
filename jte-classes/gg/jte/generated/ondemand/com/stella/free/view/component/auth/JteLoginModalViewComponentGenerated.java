package gg.jte.generated.ondemand.com.stella.free.view.component.auth;
import de.tschuehly.spring.viewcomponent.jte.ViewContext;
public final class JteLoginModalViewComponentGenerated {
	public static final String JTE_NAME = "com/stella/free/view/component/auth/LoginModalViewComponent.jte";
	public static final int[] JTE_LINE_INFO = {0,0,4,4,4,4,4,4,6,6,6,6,45};
	private static final gg.jte.runtime.BinaryContent BINARY_CONTENT = gg.jte.runtime.BinaryContent.load(JteLoginModalViewComponentGenerated.class, "JteLoginModalViewComponentGenerated.bin", 1,2455);
	private static final byte[] TEXT_PART_BINARY_0 = BINARY_CONTENT.get(0);
	private static final byte[] TEXT_PART_BINARY_1 = BINARY_CONTENT.get(1);
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, ViewContext headerViewComponent) {
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_0);
		jteOutput.setContext("html", null);
		jteOutput.writeUserContent(headerViewComponent);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_1);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		ViewContext headerViewComponent = (ViewContext)params.get("headerViewComponent");
		render(jteOutput, jteHtmlInterceptor, headerViewComponent);
	}
}
