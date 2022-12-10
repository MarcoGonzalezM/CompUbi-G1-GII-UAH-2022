import psycopg2

class PostgresConnection:
    def __init__(self):
        self.datos = None

    def connectToPostgres(self):
        try:
            conn = psycopg2.connect(
                host="localhost",
                database="ubicua",
                port = "5432",
                user = "postgres",
                password = "postgres")
            cur = conn.cursor()

            cur.execute("SELECT * FROM Taquillero")

            self.datos = cur.fetchall()
        
        except(Exception, psycopg2.DatabaseError) as error:
            print(error)
        finally:
            if conn is not None:
                conn.close()

    def obtenerDatos(self):
        taquilleros = []

        for id_taquillero, pais, ciudad, cP, calle, num in self.datos:
            taquilleros.append("Taquillero" + id_taquillero)

        return taquilleros
        
