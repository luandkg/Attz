package apps.app_tronarko;

import libs.azzal.AzzalUnico;

public class AppTronarkos {

    public static void TRONARKO(){
        AzzalUnico.unico("Tronarko", 1550, 1000, new apps.app_tronarko.AppTronarko());
    }

    public static void ALARME(){
        AzzalUnico .unico("Tronarko.Alarme", 900, 800, new apps.app_tronarko.AppAlarme());
    }

    public static void ASTROS(){
          AzzalUnico.unico("Astros", 1550, 1100, new apps.app_tronarko.AppAstros());
    }

}
