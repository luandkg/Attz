package libs.tronarko.utils;

import libs.luan.Strings;
import libs.luan.fmt;
import libs.tronarko.Hazde;
import libs.tronarko.Tozte;
import libs.tronarko.Tron;

public class StringTronarko {

    // 2021 12 29 - CAIXA DE UTILIDADES


    public String getSuperarko(String entrada) {
        String s1 = "";
        String s2 = "";
        String s3 = "";

        int o = entrada.length();
        int i = 0;

        int e = 1;

        while (i < o) {
            String l = String.valueOf(entrada.charAt(i));
            if (l.contentEquals("/")) {
                e += 1;
            } else {

                if (e == 1) {
                    s1 += l;
                } else if (e == 2) {
                    s2 += l;
                } else if (e == 3) {
                    s3 += l;
                }

            }
            i += 1;
        }

        return s1;
    }

    public String getHiperarko(String entrada) {
        String s1 = "";
        String s2 = "";
        String s3 = "";

        int o = entrada.length();
        int i = 0;

        int e = 1;

        while (i < o) {
            String l = String.valueOf(entrada.charAt(i));
            if (l.contentEquals("/")) {
                e += 1;
            } else {

                if (e == 1) {
                    s1 += l;
                } else if (e == 2) {
                    s2 += l;
                } else if (e == 3) {
                    s3 += l;
                }

            }
            i += 1;
        }

        return s2;
    }

    public String getTronarko(String entrada) {
        String s1 = "";
        String s2 = "";
        String s3 = "";

        int o = entrada.length();
        int i = 0;

        int e = 1;

        while (i < o) {
            String l = String.valueOf(entrada.charAt(i));
            if (l.contentEquals("/")) {
                e += 1;
            } else if (l.contentEquals(" ")) {
                e += 1;
            } else {

                if (e == 1) {
                    s1 += l;
                } else if (e == 2) {
                    s2 += l;
                } else if (e == 3) {
                    s3 += l;
                }

            }
            i += 1;
        }

        return s3;
    }

    public String getArkoFormatoComplexo(String entrada) {

        String s1 = "";
        String s2 = "";
        String s3 = "";

        String s4 = "";
        String s5 = "";
        String s6 = "";

        int o = entrada.length();
        int i = 0;

        int e1 = 1;
        int e2 = 0;

        while (i < o) {
            String l = String.valueOf(entrada.charAt(i));
            if (e1 <= 3 && l.contentEquals("/")) {
                e1 += 1;
            } else if (e1 == 3 && e2 == 0 && l.contentEquals(" ")) {
                e2 = 1;
            } else if (e1 == 3 && e2 <= 3 && l.contentEquals(":")) {
                e2 += 1;
            } else {

                if (e2 == 0) {

                    if (e1 == 1) {
                        s1 += l;
                    } else if (e1 == 2) {
                        s2 += l;
                    } else if (e1 == 3) {
                        s3 += l;
                    }

                } else {

                    if (e2 == 1) {
                        s4 += l;
                    } else if (e2 == 2) {
                        s5 += l;
                    } else if (e2 == 3) {
                        s6 += l;
                    }

                }


            }
            i += 1;
        }
        if (s4.length() == 0) {
            s4 = "0";
        }
        return s4;
    }

    public String getIttasFormatoComplexo(String entrada) {

        String s1 = "";
        String s2 = "";
        String s3 = "";

        String s4 = "";
        String s5 = "";
        String s6 = "";

        int o = entrada.length();
        int i = 0;

        int e1 = 1;
        int e2 = 0;

        while (i < o) {
            String l = String.valueOf(entrada.charAt(i));
            if (e1 <= 3 && l.contentEquals("/")) {
                e1 += 1;
            } else if (e1 == 3 && e2 == 0 && l.contentEquals(" ")) {
                e2 = 1;
            } else if (e1 == 3 && e2 <= 3 && l.contentEquals(":")) {
                e2 += 1;
            } else {

                if (e2 == 0) {

                    if (e1 == 1) {
                        s1 += l;
                    } else if (e1 == 2) {
                        s2 += l;
                    } else if (e1 == 3) {
                        s3 += l;
                    }

                } else {

                    if (e2 == 1) {
                        s4 += l;
                    } else if (e2 == 2) {
                        s5 += l;
                    } else if (e2 == 3) {
                        s6 += l;
                    }

                }


            }
            i += 1;
        }

        if (s5.length() == 0) {
            s5 = "0";
        }
        return s5;
    }

    public String getUzzonFormatoComplexo(String entrada) {

        String s1 = "";
        String s2 = "";
        String s3 = "";

        String s4 = "";
        String s5 = "";
        String s6 = "";

        int o = entrada.length();
        int i = 0;

        int e1 = 1;
        int e2 = 0;

        while (i < o) {
            String l = String.valueOf(entrada.charAt(i));
            if (e1 <= 3 && l.contentEquals("/")) {
                e1 += 1;
            } else if (e1 == 3 && e2 == 0 && l.contentEquals(" ")) {
                e2 = 1;
            } else if (e1 == 3 && e2 <= 3 && l.contentEquals(":")) {
                e2 += 1;
            } else {

                if (e2 == 0) {

                    if (e1 == 1) {
                        s1 += l;
                    } else if (e1 == 2) {
                        s2 += l;
                    } else if (e1 == 3) {
                        s3 += l;
                    }

                } else {

                    if (e2 == 1) {
                        s4 += l;
                    } else if (e2 == 2) {
                        s5 += l;
                    } else if (e2 == 3) {
                        s6 += l;
                    }

                }


            }
            i += 1;
        }
        return s6;
    }


    public static Tozte TOZTE_PARSER(String entrada) {


        String s1 = "";
        String s2 = "";
        String s3 = "";

        int o = entrada.length();
        int i = 0;

        int e = 1;

        while (i < o) {
            String l = String.valueOf(entrada.charAt(i));
            if (l.contentEquals("/")) {
                e += 1;
            } else {

                if (e == 1) {
                    s1 += l;
                } else if (e == 2) {
                    s2 += l;
                } else if (e == 3) {
                    s3 += l;
                }

            }
            i += 1;
        }


        int s = Integer.parseInt(s1);
        int h = Integer.parseInt(s2);
        int t = Integer.parseInt(s3);

        return new Tozte(s, h, t);
    }

    public static Hazde HAZDE_PARSER(String entrada) {


        String s1 = "";
        String s2 = "";
        String s3 = "";

        int o = entrada.length();
        int index = 0;

        int e = 1;

        while (index < o) {
            String l = String.valueOf(entrada.charAt(index));
            if (l.contentEquals(":")) {
                e += 1;
            } else {

                if (e == 1) {
                    s1 += l;
                } else if (e == 2) {
                    s2 += l;
                } else if (e == 3) {
                    s3 += l;
                }

            }
            index += 1;
        }


        int a = Integer.parseInt(s1);
        int i = Integer.parseInt(s2);
        int u = Integer.parseInt(s3);

        return new Hazde(a, i, u);
    }

    public Hazde parseHazdeSemUzzons(String entrada) {


        String s1 = "";
        String s2 = "";

        int o = entrada.length();
        int index = 0;

        int e = 1;

        while (index < o) {
            String l = String.valueOf(entrada.charAt(index));
            if (l.contentEquals(":")) {
                e += 1;
            } else {

                if (e == 1) {
                    s1 += l;
                } else if (e == 2) {
                    s2 += l;
                }

            }
            index += 1;
        }


        int a = Integer.parseInt(s1);
        int i = Integer.parseInt(s2);
        int u = 0;

        return new Hazde(a, i, u);
    }


    public String getArko(String entrada) {

        if (entrada.contains(" ")) {
            return getArkoFormatoComplexo(entrada);
        } else {
            return parseArko(entrada);
        }

    }

    public String getIttas(String entrada) {

        if (entrada.contains(" ")) {
            return getIttasFormatoComplexo(entrada);
        } else {
            return parseIttas(entrada);
        }

    }

    public String getUzzon(String entrada) {

        if (entrada.contains(" ")) {
            return getUzzonFormatoComplexo(entrada);
        } else {
            return parseUzzon(entrada);
        }

    }


    public String parseArko(String entrada) {


        String s1 = "";
        String s2 = "";
        String s3 = "";

        int o = entrada.length();
        int index = 0;

        int e = 1;

        while (index < o) {
            String l = String.valueOf(entrada.charAt(index));
            if (l.contentEquals(":")) {
                e += 1;
            } else {

                if (e == 1) {
                    s1 += l;
                } else if (e == 2) {
                    s2 += l;
                } else if (e == 3) {
                    s3 += l;
                }

            }
            index += 1;
        }

        return s1;
    }

    public String parseIttas(String entrada) {


        String s1 = "";
        String s2 = "";
        String s3 = "";

        int o = entrada.length();
        int index = 0;

        int e = 1;

        while (index < o) {
            String l = String.valueOf(entrada.charAt(index));
            if (l.contentEquals(":")) {
                e += 1;
            } else {

                if (e == 1) {
                    s1 += l;
                } else if (e == 2) {
                    s2 += l;
                } else if (e == 3) {
                    s3 += l;
                }

            }
            index += 1;
        }

        return s2;
    }

    public String parseUzzon(String entrada) {


        String s1 = "";
        String s2 = "";
        String s3 = "";

        int o = entrada.length();
        int index = 0;

        int e = 1;

        while (index < o) {
            String l = String.valueOf(entrada.charAt(index));
            if (l.contentEquals(":")) {
                e += 1;
            } else {

                if (e == 1) {
                    s1 += l;
                } else if (e == 2) {
                    s2 += l;
                } else if (e == 3) {
                    s3 += l;
                }

            }
            index += 1;
        }

        return s3;
    }


    public Hazde getHazdeDeComplexoSemUzzon(String entrada) {

        int a = Integer.parseInt(getArkoFormatoComplexo(entrada));
        int i = Integer.parseInt(getIttasFormatoComplexo(entrada));

        return new Hazde(a, i, 0);
    }

    public Tozte getTozteDeComplexo(String entrada) {

        int s = Integer.parseInt(getSuperarko(entrada));
        int h = Integer.parseInt(getHiperarko(entrada));
        int t = Integer.parseInt(getTronarko(entrada));

        return new Tozte(s, h, t);
    }

    public Hazde getHazdeDeComplexo(String entrada) {

        int a = Integer.parseInt(getArkoFormatoComplexo(entrada));
        int i = Integer.parseInt(getIttasFormatoComplexo(entrada));
        int u = Integer.parseInt(getUzzonFormatoComplexo(entrada));

        return new Hazde(a, i, u);
    }

    public Tron getTronDeComplexo(String entrada) {

        int s = Integer.parseInt(getSuperarko(entrada));
        int h = Integer.parseInt(getHiperarko(entrada));
        int t = Integer.parseInt(getTronarko(entrada));

        int a = Integer.parseInt(getArkoFormatoComplexo(entrada));
        int i = Integer.parseInt(getIttasFormatoComplexo(entrada));
        int u = 0;

        if (contar(entrada, ":") == 2) {
            u = Integer.parseInt(getUzzonFormatoComplexo(entrada));
        }

        return new Tron(a, i, u, s, h, t);
    }

    private int contar(String entrada, String proc) {

        int o = entrada.length();
        int index = 0;

        int c = 0;

        while (index < o) {
            String l = String.valueOf(entrada.charAt(index));
            if (l.contentEquals(proc)) {
                c += 1;
            }
            index += 1;
        }

        return c;

    }


    public static Tron PARSER_TRON(String entrada) {

        int i_superarko = Integer.parseInt(Strings.GET_SEQUENCIAL(entrada,0,2));
        int i_hiperarko = Integer.parseInt(Strings.GET_SEQUENCIAL(entrada,3,2));
        int i_tronarko = Integer.parseInt(Strings.GET_SEQUENCIAL(entrada,6,4));

        int i_arco = Integer.parseInt(Strings.GET_SEQUENCIAL(entrada,11,2));
        int i_itta = Integer.parseInt(Strings.GET_SEQUENCIAL(entrada,14,2));
        int i_uzzon = Integer.parseInt(Strings.GET_SEQUENCIAL(entrada,17,2));

        Tron ret = new Tron(i_arco, i_itta, i_uzzon, i_superarko, i_hiperarko, i_tronarko);

        return ret;
    }

    public static Tozte PARSER_TOZTE(String entrada) {

        int i_superarko = Integer.parseInt(Strings.GET_SEQUENCIAL(entrada,0,2));
        int i_hiperarko = Integer.parseInt(Strings.GET_SEQUENCIAL(entrada,3,2));
        int i_tronarko = Integer.parseInt(Strings.GET_SEQUENCIAL(entrada,6,4));


        Tozte ret = new Tozte(i_superarko, i_hiperarko, i_tronarko);
        return ret;
    }

    public static String SUPERARKOS_DO_TRONARKO_PARA_TOZTE(int superarkos, int eTronarko) {

        int hiperarko = 1;
        while (superarkos > 50) {
            hiperarko += 1;
            superarkos -= 50;
        }

        return fmt.numero_zerado_c2(superarkos) + "/" + fmt.numero_zerado_c2(hiperarko) + "/" + eTronarko;
    }
}
