public class GestionProductosServidor {
    public GestionProductosServidor(Producto[] misProductos) {
        this.misProductos = misProductos;
    }

    public Producto[] generarProductos() {
        for(int i = 0; i < misProductos.length; i += 1) 
            misProductos[i] = new Producto(id[i], nombre[i], precio[i], generarExistencias(), descripcion[i], false, imagen[i]);
        
        return misProductos;
    }

    public int generarExistencias() {
        return (int)((Math.random() * ((100 - 50) + 1)) + 50);
    }

    private Producto[] misProductos;
    private int[] id = {1,2,3,4,5,6,7,8,9,10};
    private String[] nombre = {"Tazas", "Leche", "Audifonos", "Controles", " Juguetes", "Tortillas", "Gorras", "Celulares", "Pan", "Cafe"};
    private double[] precio = {25.50, 20.0, 850.0, 450.25, 360.95, 15.25, 200.56, 1500.58, 6.70, 29.99};
    private String[] descripcion = {
        "Tazas chidas",
        "Santaclara, la mejor",
        "Skullcandy ink'd wireless 2.0",
        "Control universal",
        "Juguete variado",
        "Tortillas frescas, no existe duras",
        "Gorras diversos modelos",
        "Celulares pa'l kevin",
        "Pan hecho en casa",
        "Cafe barato pero bueno"
    };
    private String[] imagen = {
        "Tazas chidas",
        "Leche Santaclara",
        "Audifonos Skullcandy",
        "Control universal",
        "Juguetes diversos",
        "Tortillas",
        "Gorras bordadas",
        "Celulares Chinos",
        "Pan de casa",
        "Cafe"
    };
}