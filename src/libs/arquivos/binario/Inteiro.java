package libs.arquivos.binario;

import libs.luan.*;

import java.io.IOException;

public class Inteiro {

    public static int byteToInt(byte b) {

        if (b >= 0) {
            return (int) b;
        } else {
            return 256 + (int) b;
        }

    }




    public static int get_u32(Vetor<Byte> bytes, int index) {
        int i = 0;

            i = ((bytes.get(index+3) & 0xFF) << 24) |
                    ((bytes.get(index+2) & 0xFF) << 16) |
                    ((bytes.get(index+1)& 0xFF) << 8) |
                    ((bytes.get(index) & 0xFF) << 0);


        return i;
    }


    public static int get_u16_le(Vetor<Byte> bytes, int index){
        return (Inteiro.byteToInt(bytes.get(index))*256)+Inteiro.byteToInt(bytes.get(index+1));
    }

    public static int get_u16_be(Vetor<Byte> bytes, int index){
        return (Inteiro.byteToInt(bytes.get(index+1))*256)+Inteiro.byteToInt(bytes.get(index));
    }


    public static long get_u64(byte[] bytes) {
        long result = 0;

            byte[] lendo = new byte[8];
            for (int i = 0; i < 8; i++) {
                lendo[i] = bytes[i];
            }
            for (int i = 0; i < 8; i++) {
                result <<= Long.BYTES;
                result |= (lendo[i] & 0xFF);
            }

        return result;
    }

    public static Ordenavel<Integer> GET_ORDENAVEL(){
       return new Ordenavel<Integer>() {
            @Override
            public int emOrdem(Integer a, Integer b) {
                return Integer.compare(a,b);
            }
        };
    }

    public static Igualavel<Integer> IGUALAVEL() {
        return new Igualavel<Integer>() {
            @Override
            public boolean is(Integer a, Integer b) {
                return a.intValue()==b.intValue();
            }

        };
    }

    public static Hasher<Integer> HASH() {
        return new Hasher<Integer>() {
            @Override
            public int hash(Integer eObjeto) {
                return eObjeto.hashCode();
            }
        };
    }


    public static Igualdade<Integer> IGUALDADE() {
        return new Igualdade<Integer>() {
            @Override
            public boolean isIgual(Integer v1, Integer v2) {
                return v1.equals(v2);
            }
        };
    }

    public static int MODULO(int valor){
        if(valor<0){
            return valor*(-1);
        }else{
            return valor;
        }
    }
}
