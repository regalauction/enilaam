package in.regalauction.interfaces.web.spotdeal;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import in.regalauction.domain.model.spotdeal.SpotDealItem;

public class SpotDealPurchaseForm extends SpotDealItemForm {
	
	@NotNull
	@Min(1)
	@Digits(integer = 8, fraction = 2)
	private BigDecimal orderQuantity;
	
	
	public SpotDealPurchaseForm() {
		super();
	}

	public SpotDealPurchaseForm(SpotDealItem spotDealItem) throws IllegalAccessException, InvocationTargetException {
		super(spotDealItem);
	}

	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}	
	
	
}
