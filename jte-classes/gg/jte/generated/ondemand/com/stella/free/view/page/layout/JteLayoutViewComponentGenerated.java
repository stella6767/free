package gg.jte.generated.ondemand.com.stella.free.view.page.layout;
import de.tschuehly.spring.viewcomponent.jte.ViewContext;
public final class JteLayoutViewComponentGenerated {
	public static final String JTE_NAME = "com/stella/free/view/page/layout/LayoutViewComponent.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,2,2,2,2,6,6,6,6,8,8,8,10,10,10};
	private static final gg.jte.runtime.BinaryContent BINARY_CONTENT = gg.jte.runtime.BinaryContent.load(JteLayoutViewComponentGenerated.class, "JteLayoutViewComponentGenerated.bin", 1,2,2);
	private static final byte[] TEXT_PART_BINARY_0 = BINARY_CONTENT.get(0);
	private static final byte[] TEXT_PART_BINARY_1 = BINARY_CONTENT.get(1);
	private static final byte[] TEXT_PART_BINARY_2 = BINARY_CONTENT.get(2);
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, ViewContext footer, ViewContext header, ViewContext nestedView) {
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_0);
		jteOutput.setContext("html", null);
		jteOutput.writeUserContent(header);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_1);
		jteOutput.setContext("html", null);
		jteOutput.writeUserContent(nestedView);
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_2);
		jteOutput.setContext("html", null);
		jteOutput.writeUserContent(footer);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		ViewContext footer = (ViewContext)params.get("footer");
		ViewContext header = (ViewContext)params.get("header");
		ViewContext nestedView = (ViewContext)params.get("nestedView");
		render(jteOutput, jteHtmlInterceptor, footer, header, nestedView);
	}
}
