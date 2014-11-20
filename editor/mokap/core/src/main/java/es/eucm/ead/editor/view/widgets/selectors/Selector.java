package es.eucm.ead.editor.view.widgets.selectors;

public interface Selector<T> {

	public void prepare(SelectorListener<T> selectorListener, Object... args);

	public interface SelectorListener<T> {

		public void selected(T selected);

		public void cancelled();

	}
}
