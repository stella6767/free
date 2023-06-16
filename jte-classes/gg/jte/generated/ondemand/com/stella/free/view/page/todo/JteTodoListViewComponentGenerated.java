package gg.jte.generated.ondemand.com.stella.free.view.page.todo;
import com.stella.free.entity.Todo;
import com.stella.free.view.component.TodoViewComponent;
import com.stella.free.view.component.pagination.PaginationViewComponent;
import de.tschuehly.spring.viewcomponent.jte.ViewContext;
import org.springframework.data.domain.Page;
import java.util.*;
public final class JteTodoListViewComponentGenerated {
	public static final String JTE_NAME = "com/stella/free/view/page/todo/TodoListViewComponent.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,4,5,7,7,7,7,7,7,7,7,7,40,40,40,42,42,42,44,44,51,51,51,52};
	private static final gg.jte.runtime.BinaryContent BINARY_CONTENT = gg.jte.runtime.BinaryContent.load(JteTodoListViewComponentGenerated.class, "JteTodoListViewComponentGenerated.bin", 1094,22,14,37,1);
	private static final byte[] TEXT_PART_BINARY_0 = BINARY_CONTENT.get(0);
	private static final byte[] TEXT_PART_BINARY_1 = BINARY_CONTENT.get(1);
	private static final byte[] TEXT_PART_BINARY_2 = BINARY_CONTENT.get(2);
	private static final byte[] TEXT_PART_BINARY_3 = BINARY_CONTENT.get(3);
	private static final byte[] TEXT_PART_BINARY_4 = BINARY_CONTENT.get(4);
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Page<Todo> todos, TodoViewComponent todoViewComponent, PaginationViewComponent paginationViewComponent) {
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_0);
		for (Todo todo : todos) {
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_1);
			jteOutput.setContext("div", null);
			jteOutput.writeUserContent(todoViewComponent.render(todo));
			jteOutput.writeBinaryContent(TEXT_PART_BINARY_2);
		}
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_3);
		jteOutput.setContext("html", null);
		jteOutput.writeUserContent(paginationViewComponent.render(todos, "todos"));
		jteOutput.writeBinaryContent(TEXT_PART_BINARY_4);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Page<Todo> todos = (Page<Todo>)params.get("todos");
		TodoViewComponent todoViewComponent = (TodoViewComponent)params.get("todoViewComponent");
		PaginationViewComponent paginationViewComponent = (PaginationViewComponent)params.get("paginationViewComponent");
		render(jteOutput, jteHtmlInterceptor, todos, todoViewComponent, paginationViewComponent);
	}
}
