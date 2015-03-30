package com.monex.bancoliquidador.ejb;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.ejb.CreateException;

import com.monex.comun.bd.MatrizDatos;
import com.monex.bancoliquidador.bd.dao.SaldosBancosDAO;

public class BancoLiquidadorCCUSDEJBSLBean implements SessionBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2564900402301723214L;
	SessionContext sessionContext;

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

	public MatrizDatos getMonitorSaldos(String sCveEntidad, String sFecha) {
		SaldosBancosDAO dao = null;
		MatrizDatos saldos = null;

		dao = new SaldosBancosDAO(sCveEntidad);
		saldos = dao.getSaldos(sFecha);

		return saldos;
	}
}
