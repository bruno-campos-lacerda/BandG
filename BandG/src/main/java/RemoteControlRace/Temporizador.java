package RemoteControlRace;

public class Temporizador implements Runnable {
    private final AtualizadorTela tela;
    private boolean rodando;
    private int tempoTotal;
    private int equipe;
    private int min=0, seg=0, mil=0;
    //private float mil=0;
    private float deltaTime, timeInterval, oldTimeInterval;

    public Temporizador(AtualizadorTela tela, int equipe) {
        this.tela = tela;
        this.equipe = equipe;
    }

    public void iniciar() {
        rodando = true;
        new Thread(this).start();
        System.out.println(Thread.currentThread().getName());
    }

    public void parar() {
        rodando = false;
    }

    public void zerar() {
        //tempoTotal = 0;
        this.mil=0;
        this.seg=0;
        this.min=0;
    }

    public int getTempoTotal() {
        return tempoTotal;
    }
    
    public int getMil(){
        return this.mil;
    }
    public int getSeg(){
        return this.seg;
    }
    public int getMin(){
        return this.min;
    }
    
    @Override
    public void run() {
        while (rodando) {
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.timeInterval = System.nanoTime();
            this.deltaTime = (timeInterval - oldTimeInterval) / 1000000;
            this.oldTimeInterval = timeInterval;
            
            tempoTotal += deltaTime;
            mil += deltaTime;
            if(mil >= 1000){
                seg++;
                mil=0;
                if(seg >= 60){
                    min++;
                    seg=0;
                }
            }
            tela.atualizarTempo(min, seg, mil);
        }
    }
}