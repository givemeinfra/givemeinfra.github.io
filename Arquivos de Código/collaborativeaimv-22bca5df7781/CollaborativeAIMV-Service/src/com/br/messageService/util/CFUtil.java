package com.br.messageService.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CFUtil {

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object value) 
	{
		if (value == null) {
			return true;
		} 
		if ("".equals(value)){
			return true;
		}
		else if (value instanceof Collection) {
			return ((Collection) value).isEmpty();
		}
		return false;
	}
	
	public static boolean isEmailValid(String email) {
		Pattern patterns = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher matcher = patterns.matcher(email);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}
	
	public static boolean isCPFValid(String strCpf) {
		strCpf = CFUtil.replace(strCpf, ".", "");
		strCpf = CFUtil.replace(strCpf, "-", "");

		int d1, d2;
		int digito1, digito2, resto;
		int digitoCPF;
		String nDigResult;

		d1 = d2 = 0;
		digito1 = digito2 = resto = 0;

		for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
			digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount))
					.intValue();

			//multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4
			// e assim por diante.
			d1 = d1 + (11 - nCount) * digitoCPF;

			//para o segundo digito repita o procedimento incluindo o primeiro
			// digito calculado no passo anterior.
			d2 = d2 + (12 - nCount) * digitoCPF;
		}
		;

		//Primeiro resto da divisão por 11.
		resto = (d1 % 11);

		//Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
		// menos o resultado anterior.
		if (resto < 2){
			digito1 = 0;
		}else{
			digito1 = 11 - resto;
		}
		
		d2 += 2 * digito1;

		//Segundo resto da divisão por 11.
		resto = (d2 % 11);

		//Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
		// menos o resultado anterior.
		if (resto < 2){
			digito2 = 0;
		}else{
			digito2 = 11 - resto;
		}	
		//Digito verificador do CPF que está sendo validado.
		String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf
				.length());

		//Concatenando o primeiro resto com o segundo.
		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

		//comparar o digito verificador do cpf com o primeiro resto + o segundo
		// resto.
		return nDigVerific.equals(nDigResult);
	}
	
	public static String replace(String text, String repl, String with) {
		return replace(text, repl, with, -1);
	}
	
	/**
	 * <p>
	 * Replaces a String with another String inside a larger String, for the
	 * first <code>max</code> values of the search String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> reference passed to this method is a no-op.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.replace(null, *, *, *)         = null
	 *  StringUtils.replace(&quot;&quot;, *, *, *)           = &quot;&quot;
	 *  StringUtils.replace(&quot;abaa&quot;, null, null, 1) = &quot;abaa&quot;
	 *  StringUtils.replace(&quot;abaa&quot;, null, null, 1) = &quot;abaa&quot;
	 *  StringUtils.replace(&quot;abaa&quot;, &quot;a&quot;, null, 1)  = &quot;abaa&quot;
	 *  StringUtils.replace(&quot;abaa&quot;, &quot;a&quot;, &quot;&quot;, 1)    = &quot;abaa&quot;
	 *  StringUtils.replace(&quot;abaa&quot;, &quot;a&quot;, &quot;z&quot;, 0)   = &quot;abaa&quot;
	 *  StringUtils.replace(&quot;abaa&quot;, &quot;a&quot;, &quot;z&quot;, 1)   = &quot;zbaa&quot;
	 *  StringUtils.replace(&quot;abaa&quot;, &quot;a&quot;, &quot;z&quot;, 2)   = &quot;zbza&quot;
	 *  StringUtils.replace(&quot;abaa&quot;, &quot;a&quot;, &quot;z&quot;, -1)  = &quot;zbzz&quot;
	 *  
	 * </pre>
	 * 
	 * @param text
	 *            text to search and replace in, may be null
	 * @param repl
	 *            the String to search for, may be null
	 * @param with
	 *            the String to replace with, may be null
	 * @param max
	 *            maximum number of values to replace, or <code>-1</code> if
	 *            no maximum
	 * @return the text with any replacements processed, <code>null</code> if
	 *         null String input
	 */
	public static String replace(String text, String repl, String with, int max) {
		if (text == null || repl == null || with == null || repl.length() == 0
				|| max == 0) {
			return text;
		}

		StringBuffer buf = new StringBuffer(text.length());
		int start = 0, end = 0;
		while ((end = text.indexOf(repl, start)) != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + repl.length();

			if (--max == 0) {
				break;
			}
		}
		buf.append(text.substring(start));
		return buf.toString();
	}
}
