package com.br.collaborativeAIMV.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.br.collaborativeAIMV.control.LoginControl;
import com.br.service.valueObject.DeveloperVO;
import com.br.service.valueObject.MessageVO;

public abstract class CollaborativeFilter {

	private static String ACTIVITY;
	private static String DEVELOPER;
	private static String INITIAL_DATE;
	private static String FINAL_DATE;
	
	public static String getACTIVITY() {
		return ACTIVITY;
	}
	public static void setACTIVITY(String aCTIVITY) {
		ACTIVITY = aCTIVITY;
	}
	public static String getDEVELOPER() {
		return DEVELOPER;
	}
	public static void setDEVELOPER(String dEVELOPER) {
		DEVELOPER = dEVELOPER;
	}
	public static String getINITIAL_DATE() {
		return INITIAL_DATE;
	}
	public static void setINITIAL_DATE(String iNITIAL_DATE) {
		
		if (iNITIAL_DATE!=null && !"".equals(iNITIAL_DATE)){
			SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date dateTmp1 = null;
			try {
				dateTmp1 = fmt.parse(iNITIAL_DATE);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SimpleDateFormat fmtXml = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm:ss");
			INITIAL_DATE = fmtXml.format(dateTmp1);
		}
		else{
			INITIAL_DATE = iNITIAL_DATE;
		}
	}
	public static String getFINAL_DATE() {
		return FINAL_DATE;
	}
	public static void setFINAL_DATE(String fINAL_DATE) {
		if (fINAL_DATE!=null && !"".equals(fINAL_DATE)){
			SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date dateTmp1 = null;
			try {
				dateTmp1 = fmt.parse(fINAL_DATE);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SimpleDateFormat fmtXml = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm:ss");
			FINAL_DATE = fmtXml.format(dateTmp1);
		}
		else{
			FINAL_DATE = fINAL_DATE;
		}
	}
	
	public static MessageVO getMESSAGEVO_FILTER(){
		
		MessageVO messageVO = new MessageVO();
		
		DeveloperVO developerVO = new DeveloperVO();
		developerVO.setName(DEVELOPER);
		messageVO.setDeveloperVO(developerVO);
		messageVO.getDeveloperVO().setProjectVO(LoginControl.GET_INSTANCE().currentProject);
		
		messageVO.setActivity(ACTIVITY);
		messageVO.setInitialTime(INITIAL_DATE);
		messageVO.setFinalTime(FINAL_DATE);
		
		return messageVO;
	}
}
