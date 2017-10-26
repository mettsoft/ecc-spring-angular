package com.ecc.servlets.util.app;

import java.io.PrintWriter;
import java.util.Map;
import java.util.List;

public class TemplateEngine {
	private static final String UPDATE_BUTTON_FORM = "<form action=\"%s\" method=\"GET\"><input type=\"hidden\" name=\"id\" value=\"%s\">%s<button>Edit</button></form>";
	private static final String DELETE_BUTTON_FORM = "<form action=\"%s\" method=\"POST\"><input type=\"hidden\" name=\"id\" value=\"%s\">%s<input type=\"hidden\" name=\"mode\" value=\"3\"><button onClick=\"return confirm('Are you sure you want to delete this entry?')\">Delete</button></form>";
	private PrintWriter printWriter;

	public TemplateEngine(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	public void render(String template, Map<String, Object> parameters) {
		for (Map.Entry<String, Object> parameter: parameters.entrySet()) {
			if (parameter.getValue() != null) {
				template = template.replaceAll(parameter.getKey(), parameter.getValue().toString());			
			}
		}
		template = template.replaceAll(":\\w+", "");
		printWriter.println(template);
	}

	public String generateTable(List<String> headers, List<List<String>> data, String action) {
		return generateTable(headers, data, action, "");
	}

	public String generateTable(List<String> headers, List<List<String>> data, String action, String additionalFormElements) {
		if (data.size() == 0) {
			return "<h6>No records found!</h6>";
		}

		StringBuilder builder = new StringBuilder("<table><thead>");
		for (String header: headers) {
			builder.append(String.format("<th>%s</th>", header));
		}
		builder.append("<th></th><th></th>");
		builder.append("</thead><tbody>");

		for (List<String> row: data) {
			builder.append("<tr>");
			for (String column: row) {
				builder.append(String.format("<td>%s</td>", column));
			}
			String updateButton = String.format(UPDATE_BUTTON_FORM, action, row.get(0), additionalFormElements);
			String deleteButton = String.format(DELETE_BUTTON_FORM, action, row.get(0), additionalFormElements);
			builder.append(String.format("<td>%s</td><td>%s</td></tr>", updateButton, deleteButton));
		}
		builder.append("</tbody></table>");

		return builder.toString();
	}
}
