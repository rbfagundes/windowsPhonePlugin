using System.Text;

namespace Cordova.Extension.Commands
{


    public class FgCrypto
    {

        // Chaves usadas na criptografia
        private const int c1 = 33598;
        private const int c2 = 24219;
        private const int chave = 5078;

        
        public static char[] Criptografar(char[] pTexto)
        {
            int i;
            char caracter;
            char vchave;

            vchave = (char)(pTexto.Length + chave);
            for (i = 0; i < pTexto.Length; i++)
            {
                caracter = (char)(pTexto[i] ^ (vchave >> 8));
                pTexto[i] = caracter;
                vchave = (char)(((sbyte)caracter + vchave) * c1 + c2);
            }
            return pTexto;
        }        

        
        public static char[] Decriptografar(char[] pTexto)
        {
            int i;
            char caracter;
            char vchave;
            char caracterOld;

            vchave = (char)(pTexto.Length + chave);
            for (i = 0; i < pTexto.Length; i++)
            {
                caracterOld = pTexto[i];
                caracter = (char)(caracterOld ^ (vchave >> 8));
                pTexto[i] = caracter;
                vchave = (char)(((sbyte)caracterOld + vchave) * c1 + c2);
            }
            return pTexto;
        }
    }

}