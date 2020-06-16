package org.geogebra.web.full.gui.view.algebra;

import java.util.Iterator;
import java.util.TreeSet;

import org.geogebra.common.gui.inputfield.HasLastItem;
import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.util.StringUtil;
import org.geogebra.common.util.ToStringConverter;

/**
 * Serializes construction items for keyboard input.
 */
public final class ConstructionItemProvider implements HasLastItem {

	private final Construction cons;
	private final AlgebraViewW algebraView;
	private final ToStringConverter<GeoElement> converter;
	private boolean isLastItemSimpleNumber;
	private boolean isLastItemText;

	/**
	 * @param cons construction
	 * @param algebraView Algebra view
	 */
	public ConstructionItemProvider(Construction cons, AlgebraViewW algebraView,
									ToStringConverter<GeoElement> converter) {
		this.cons = cons;
		this.algebraView = algebraView;
		this.converter = converter;
	}

	@Override
	public String getLastItem() {
		return convertToString(getLastGeoElement());
	}

	private String convertToString(GeoElement element) {
		if (element != null) {
			return converter.convert(element);
		}
		return "";
	}

	private GeoElement getLastGeoElement() {
		TreeSet<GeoElement> elements = cons.getGeoSetWithCasCellsConstructionOrder();
		Iterator<GeoElement> iterator = elements.descendingIterator();
		GeoElement element = iterator.next();
		while (algebraView.getNode(element) == null && iterator.hasNext()) {
			element = iterator.next();
		}
		setLastItemFlagsWith(element);
		return element;
	}

	private void setLastItemFlagsWith(GeoElement lastItem) {
		String lastItemString = convertToString(lastItem);
		isLastItemSimpleNumber = StringUtil.isSimpleNumber(lastItemString);
		isLastItemText = lastItem != null && lastItem.isGeoText();
	}

	@Override
	public boolean isLastItemSimpleNumber() {
		return isLastItemSimpleNumber;
	}

	@Override
	public boolean isLastItemText() {
		return isLastItemText;
	}
}