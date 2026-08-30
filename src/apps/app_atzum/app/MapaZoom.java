package apps.app_atzum.app;

import libs.azzal.Cores;
import libs.azzal.Renderizador;
import libs.azzal.geometria.Ponto;
import libs.imagem.Imagem;

import java.awt.image.BufferedImage;

public class MapaZoom {

    private boolean drone_ultimo_valido = false;
    private int drone_ultimo_px = 0;
    private int drone_ultimo_py = 0;

    private AppAtzum mApp;

    private BufferedImage mapa_drone = null;
    private boolean drone_ok = false;
    private Renderizador render_drone;

    private Cores mCores;

    private int mPosX;
    private int mPosY;

    private int mLargura;
    private int mAltura;

    public MapaZoom(AppAtzum eApp,int ePX,int ePY) {
        mApp = eApp;

        mPosX=ePX;
        mPosY=ePY;

        mLargura=300;
        mAltura=300;

        mCores = new Cores();
        mapa_drone = Imagem.criarEmBranco(mLargura, mAltura);
        render_drone = new Renderizador(mapa_drone);



    }

    public int getPosX(){
        return mPosX;
    }

    public int getPosY(){
        return mPosY;
    }

    public int getLargura(){
        return mLargura;
    }

    public int getAltura(){
        return mAltura;
    }


    public void update(boolean ultimo) {

        if (ultimo && !drone_ultimo_valido) {
            return;
        }

        int mGPS_PX = mApp.mWidgetMapaVisualizador.getGPS_PX();
        int mGPS_PY = mApp.mWidgetMapaVisualizador.getGPS_PY();

        if (!ultimo) {
            drone_ultimo_valido = true;
            drone_ultimo_px = mGPS_PX;
            drone_ultimo_py = mGPS_PY;
        }

        // DRONE
        int comecar_x = mGPS_PX - 100;
        int comecar_y = mGPS_PY - 100;

        int terminar_x = mGPS_PX + 200;
        int terminar_y = mGPS_PY + 200;

        if (ultimo) {
            comecar_x = drone_ultimo_px - 100;
            comecar_y = drone_ultimo_py - 100;

            terminar_x = drone_ultimo_px + 200;
            terminar_y = drone_ultimo_py + 200;
        }

        drone_ok = true;
        render_drone.limpar(mCores.getBranco());

        mApp.mWidgetMapaVisualizador.espelhar(comecar_x, comecar_y, terminar_x, terminar_y, render_drone);


        for (Ponto cidade : mApp.mCidades) {
            if (cidade.getX() > comecar_x && cidade.getX() < terminar_x && cidade.getY() > comecar_y && cidade.getY() < terminar_y) {

                int cidade_x = cidade.getX() - comecar_x;
                int cidade_y = cidade.getY() - comecar_y;

                render_drone.drawCirculoCentralizado_Pintado(cidade_x, cidade_y, 3, mCores.getAmarelo());

                if (mApp.mCidadeSelecionada) {
                    if (mApp.mCidadeSelecionadaX == cidade.getX() && mApp.mCidadeSelecionadaY == cidade.getY()) {

                        render_drone.drawCirculoCentralizado_Pintado(cidade_x, cidade_y, 5, mCores.getVerde());
                        render_drone.drawCirculoCentralizado_Pintado(cidade_x, cidade_y, 2, mCores.getAzul());

                    }
                }


            }
        }


        int drone_x = mGPS_PX - comecar_x;
        int drone_y = mGPS_PY - comecar_y;

        render_drone.drawCirculoCentralizado_Pintado(drone_x, drone_y, 5, mCores.getVerde());


    }

    public void render(Renderizador g) {
        if (drone_ok) {
            g.drawImagem(mPosX, mPosY, mapa_drone);
        }
    }

}
