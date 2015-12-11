using System;
using System.Diagnostics;
using System.Text;
using WP7CordovaClassLib.Cordova;
using WP7CordovaClassLib.Cordova.Commands;
using System.Runtime.Serialization;

namespace Cordova.Extension.Commands
{


    public class Crypto : BaseCommand
    {
        
        private static string TAG = "CryptoPlugin: ";

        public void sum(string args)
        {
            this.DispatchCommandResult(new PluginResult(PluginResult.Status.OK, 50));
        }

        /// <summary>
        /// Encode base 64
        /// </summary>
        /// <param name="toEncode"> String do encode </param>
        /// <returns> encoded result </returns>        
        public void encodeBase64(string toEncode)
        {
            toEncode = toEncode.Substring(1, toEncode.Length - 2);
            string base64 = Convert.ToBase64String(Encoding.GetEncoding("ISO-8859-1").GetBytes(toEncode));
            
            Debug.WriteLine(TAG + "base64=" + base64);
            Debug.WriteLine(TAG, "encodeBase64 ok");

            this.DispatchCommandResult(new PluginResult(PluginResult.Status.OK, new AptmResult() { result = base64 }));
        }

        /// <summary>
        /// Decode base64
        /// </summary>
        /// <param name="toDecode"> String to decode </param>
        /// <returns> Decoded result </returns>
        public void decodeBase64(string toDecode)
        {
            toDecode = toDecode.Substring(1, toDecode.Length - 2);
            string base64 = BitConverter.ToString(Convert.FromBase64String(toDecode));

            Debug.WriteLine(TAG + "base64=" + base64);
            Debug.WriteLine(TAG, "decodeBase64 ok");

            this.DispatchCommandResult(new PluginResult(PluginResult.Status.OK, new AptmResult() { result = base64 }));
        }


        /// <summary>
        /// Crypt the value
        /// </summary>
        /// <param name="String"> text to be crypted </param>
        /// <returns> JSONObject representation of directory list. e.g
        ///         {"result":result to be sent} </returns>
        public void crypt(string toCrypt)
        {
            toCrypt = toCrypt.Substring(1, toCrypt.Length - 2);
            //byte[] arrayString = Encoding.GetEncoding("ISO-8859-1").GetBytes(toCrypt);
            //byte[] arrayCriptografado = FgCrypto.Criptografar(arrayString);
            char[] arrayCriptografado = FgCrypto.Criptografar(toCrypt.ToCharArray());

            string base64 = Convert.ToBase64String(Encoding.GetEncoding("ISO-8859-1").GetBytes(arrayCriptografado));
            //string base64 = Convert.ToBase64String(System.Text.Encoding.UTF8.GetBytes(arrayCriptografado));

            Debug.WriteLine(TAG + "base64=" + base64);
            Debug.WriteLine(TAG, "crypt ok");

            // ignore protocol first characters "×°" this will be included by Java Script communication  			
            this.DispatchCommandResult(new PluginResult(PluginResult.Status.OK, new AptmResult() { result = base64 }));
        }


        /// <summary>
        /// Decrypt the value
        /// </summary>
        /// <param name="String"> text to be decrypted </param>
        /// <returns> JSONObject representation of directory list. e.g
        ///         {"result":result to be sent} </returns>
        public void decrypt(string crypted)
        {
            // removes the µ at the beginning and ££ at end
            //string filtered = crypted.Substring(1, crypted.Length - 2 - 1);
            crypted = crypted.Substring(1, crypted.Length - 2);

            //Log.d(TAG,"filtered ="+filtered);

            byte[] arrayDados = Convert.FromBase64String(crypted);
            char[] arrayDecriptografado = FgCrypto.Decriptografar(Encoding.GetEncoding("ISO-8859-1").GetChars(arrayDados));

            string retorno = new string(arrayDecriptografado);
            Debug.WriteLine(TAG + "base64=" + retorno);
            Debug.WriteLine(TAG,"decrypt ok");
            
            this.DispatchCommandResult(new PluginResult(PluginResult.Status.OK, new AptmResult() { result = retorno }));
        }

        /// <summary>
        /// Decrypt the value and return a base64
        /// </summary>
        /// <param name="String"> text to be decrypted </param>
        /// <returns> encoded result </returns>
        public void decryptBase64(string crypted)
        {
            // removes the µ at the beginning and ££ at end
            //string filtered = crypted.Substring(1, crypted.Length - 2 - 1);
            crypted = crypted.Substring(1, crypted.Length - 2);

            //Log.d(TAG,"filtered ="+filtered);

            byte[] arrayDados = Convert.FromBase64String(crypted);
            char[] arrayDecriptografado = FgCrypto.Decriptografar(Encoding.GetEncoding("ISO-8859-1").GetChars(arrayDados));

            string retorno = new string(arrayDecriptografado);            
            string base64 = Convert.ToBase64String(Encoding.GetEncoding("ISO-8859-1").GetBytes(retorno));
            
            
            Debug.WriteLine(TAG + "base64=" + retorno);
            Debug.WriteLine(TAG, "decrypt ok");

            this.DispatchCommandResult(new PluginResult(PluginResult.Status.OK, new AptmResult() { result = base64 }));
        }
    }

    // Argument class to be serialized
    public class AptmResult
    {
        [DataMember]
        public string result;
    }

}