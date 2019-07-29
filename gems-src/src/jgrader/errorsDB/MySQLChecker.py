import mysql.connector as mc

host = "gems-db.cluster-ro-cxg9j6dtitgh.us-east-2.rds.amazonaws.com"
port = 3306
dbname = "database-1-instance-1-rds"
user = "admin"
password = "gemsTest123"

conn = mc.connect(
    host = "gems-db-instance-1.cxg9j6dtitgh.us-east-2.rds.amazonaws.com",
    port = 3306,
    user = "admin",
    passwd = "gemsTest123"
)

cursor = conn.cursor()
cursor.execute("USE errors")
cursor.execute("SELECT * FROM eMessages")

for x in cursor:
    print("=================================================================")
    print(x[0])
    print("-----------------------------------------------------------------")
    print(x[1])
    print("=================================================================")
    print("")

print(conn)
conn.commit()
conn.close()
