package br.com.fullgauge.sitradmobile;


public class FgCrypto {

	// Chaves usadas na criptografia
	private static final int c1 = 33598;
	private static final int c2 = 24219;
	private static final int chave = 5078;

	/**
	 * Criptografa uma string para ser enviada ao Sitrad Daemon O texto
	 * descriptografado ? posto na mesma string passada por par?metro.
	 * 
	 * @param pTexto
	 *            String a ser criptografada
	 * @param length
	 *            quantidade de bytes da string
	 * @return quantidade de bytes descriptografados
	 */
	public static int Criptografar(StringBuffer pTexto, int length) {
		int i;
		char caracter;
		char vchave;

		vchave = (char) (length + chave);
		for (i = 0; i < length; i++) {
			caracter = (char) (pTexto.charAt(i) ^ (vchave >> 8));
			pTexto.setCharAt(i, caracter);
			vchave = (char) (((byte) caracter + vchave) * c1 + c2);
		}
		return length;
	}

	/**
	 * Descriptografa uma string recebida do Sitrad Daemon
	 * 
	 * @param pTexto
	 *            String a ser descriptografada
	 * @param length
	 *            quantidade de bytes na string
	 * @return quantidade de bytes criptografados
	 */
	public static int Decriptografar(StringBuffer pTexto, int length) {
		int i;
		char caracter;
		char vchave;
		char caracterOld;

		vchave = (char) (length + chave);
		for (i = 0; i < length; i++) {
			caracterOld = pTexto.charAt(i);
			caracter = (char) (caracterOld ^ (vchave >> 8));
			pTexto.setCharAt(i, caracter);
			vchave = (char) (((byte) caracterOld + vchave) * c1 + c2);
		}
		return length;
	}
}
