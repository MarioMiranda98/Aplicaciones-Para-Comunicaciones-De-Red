import javax.swing.*;

public class Botones extends JButton {
    public boolean getDestapado() {
        return destapado;
    }

    public void setDestapado(boolean destapado) {
        this.destapado = destapado;
    }
    private boolean destapado;
}