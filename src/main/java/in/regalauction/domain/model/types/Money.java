package in.regalauction.domain.model.types;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.regalauction.domain.shared.ValueObject;


/**
 * This class represents money.
 * 
 * <p>Based on {@link BigDecimal}.
 * @author Diptangshu Chakrabarty
 * @version 1
 * @since 1
 * @see Price
 *
 */
public final class Money implements Comparable<Money>, ValueObject<Money> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(Money.class);

	public static final Money ZERO = new Money(BigDecimal.ZERO);
	
	private BigDecimal amount;

	public Money(BigDecimal amount) {
		this.amount = amount;
	}
	
	public Money(String amount) {
		this(new BigDecimal(amount));
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public static Money abs(final Money other) {
		return new Money(new BigDecimal(Math.abs(other.amount.doubleValue())));
	}
	
	@Override
	public int compareTo(Money other) {
		return this.amount.compareTo(other.amount);
	}
	
	public Money plus(final Money other) {
		return new Money(amount.add(other.amount));
	}
	
	public Money minus(final Money other) {
		return new Money(amount.subtract(other.amount));
	}
	
	
	public Money times(final int factor) {
		return new Money(amount.multiply(new BigDecimal(factor)));
	}
	
	public Money divide(final int divisor) {
		return new Money(amount.divide(new BigDecimal(divisor)));
	}
	
	public boolean eq(Money other) {
		return sameValueAs(other);
	}
	
	public boolean gt(Money other) {
		return compareTo(other) > 0;
	}
	public boolean lt(Money other) {
		return compareTo(other) < 0;
	}
	public boolean gteq(Money other) {
		return compareTo(other) >= 0;
	}
	public boolean lteq(Money other) {
		return compareTo(other) <= 0;
	}
	
	@Override
	public String toString() {
		return amount.toPlainString();
	}

	public boolean isMultipleOf(Money deltaMoney) {
		// TODO implementation may be improved
		LOGGER.trace("amount: {}", amount);
		LOGGER.trace("deltaMoney: {}", deltaMoney.amount);
		BigDecimal remainder = amount.remainder(deltaMoney.amount);
		LOGGER.trace("amount.remainder(deltaMoney.amount): {}", remainder);
		return remainder.compareTo(BigDecimal.ZERO) == 0;
	}
	
	
	Money() {
		// Needed by Hibernate
	}

	@Override
	public boolean sameValueAs(final Money other) {
		return other != null && this.compareTo(other) == 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		final Money other = (Money) obj;
		
		return sameValueAs(other);
	}
	
	@Override
	public int hashCode() {
		return amount.hashCode();
	}
}
