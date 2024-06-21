package br.jus.trece.blank.util.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

/**
 * conversor de índice.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@FacesConverter(value = "indiceConverter")
@SuppressWarnings("rawtypes")
public class IndiceConverter implements Converter {

	private int index = -1;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if ((value != null) && (value.length() > 0) && (!value.equals("0"))) {
			SelectItem selectItem = getSelectedItemByIndex(component, Integer.parseInt(value));

			if (selectItem != null) {
				return selectItem.getValue();
			}
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		this.index++;

		return String.valueOf(this.index);
	}

	protected SelectItem getSelectedItemByIndex(UIComponent component, int index) {
		List<SelectItem> selectItems = getSelectItems(component);
		int i = selectItems.size();

		if (index > -1 && i > index) {
			return selectItems.get(index);
		}

		return null;
	}

	protected List<SelectItem> getSelectItems(UIComponent component) {
		List<SelectItem> selectItems = new ArrayList<SelectItem>();

		int i = component.getChildCount();
		if (i == 0) {
			return selectItems;
		}

		List<UIComponent> uiComponents = component.getChildren();
		for (UIComponent uiComponent : uiComponents) {
			if (uiComponent instanceof UISelectItem) {
				addSelectItem((UISelectItem) uiComponent, selectItems);
			} else if (uiComponent instanceof UISelectItems) {
				addSelectItems((UISelectItems) uiComponent, selectItems);
			}
		}

		return selectItems;
	}

	protected void addSelectItem(UISelectItem uiItem, List<SelectItem> items) {
		boolean rendered = uiItem.isRendered();
		if (!rendered) {
			items.add(null);

			return;
		}

		Object object1 = uiItem.getValue();
		SelectItem selectItem;

		if (object1 instanceof SelectItem) {
			selectItem = (SelectItem) object1;
		} else {
			Object object2 = uiItem.getItemValue();
			String label = uiItem.getItemLabel();

			selectItem = new SelectItem(object2 == null ? "" : object2, label == null ? "" : label, uiItem.getItemDescription(), uiItem.isItemDisabled());
		}

		items.add(selectItem);
	}

	@SuppressWarnings("unchecked")
	protected void addSelectItems(UISelectItems uiItems, List<SelectItem> items) {
		boolean rendered = uiItems.isRendered();
		if (!rendered) {
			items.add(null);

			return;
		}

		Object object1 = uiItems.getValue();
		if (object1 instanceof SelectItem) {
			items.add((SelectItem) object1);
		} else if (object1 instanceof Object[]) {
			Object[] objects = (Object[]) object1;
			for (int l = 0; l < objects.length; l++) {
				if (objects[l] instanceof SelectItemGroup) {
					resolveAndAddItems((SelectItemGroup) objects[l], items);
				} else {
					items.add((SelectItem) objects[l]);
				}
			}
		} else if (object1 instanceof Collection) {
			Iterator<SelectItem> i = ((Collection<SelectItem>) object1).iterator();
			Object object2;

			while (i.hasNext()) {
				object2 = i.next();
				if (object2 instanceof SelectItemGroup) {
					resolveAndAddItems((SelectItemGroup) object2, items);
				} else {
					items.add(new SelectItem(object2));
				}
			}
		} else if (object1 instanceof Map) {
			for (Map.Entry<Object, Object> map : ((Map<Object, Object>) object1).entrySet()) {
				Object object3 = map.getKey();
				SelectItem selectItem = new SelectItem(map.getValue(), object3 == null ? (String) null : object3.toString());

				if (selectItem instanceof SelectItemGroup) {
					resolveAndAddItems((SelectItemGroup) selectItem, items);
				} else {
					items.add(selectItem);
				}
			}
		}
	}

	protected void resolveAndAddItems(SelectItemGroup group, List<SelectItem> items) {
		for (SelectItem selectItem : group.getSelectItems()) {
			if (selectItem instanceof SelectItemGroup) {
				resolveAndAddItems((SelectItemGroup) selectItem, items);
			} else {
				items.add(selectItem);
			}
		}
	}

}
