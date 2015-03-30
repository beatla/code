package com.monex.bancoliquidador.vo;

import java.io.*;

/**
 * <p>
 * Title:
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 *
 * <p>
 * Company:
 * </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DiaInhabilEUAVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1342721418912028157L;
	private String fLiquidacion;
	private String fInhabil;
	private String descInhabil;
	private String isInhabilLiquidado;

	public void setFLiquidacion(String fLiquidacion) {
		this.fLiquidacion = fLiquidacion;
	}

	public void setFInhabil(String fInhabil) {
		this.fInhabil = fInhabil;
	}

	public void setDescInhabil(String descInhabil) {
		this.descInhabil = descInhabil;
	}

	public void setIsInhabilLiquidado(String isInhabilLiquidado) {
		this.isInhabilLiquidado = isInhabilLiquidado;
	}

	public String getFLiquidacion() {
		return fLiquidacion;
	}

	public String getFInhabil() {
		return fInhabil;
	}

	public String getDescInhabil() {
		return descInhabil;
	}

	public String getIsInhabilLiquidado() {
		return isInhabilLiquidado;
	}

}
