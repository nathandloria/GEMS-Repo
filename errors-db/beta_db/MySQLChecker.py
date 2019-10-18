import mysql.connector as mc

conn = mc.connect(
    host = "test-int-database.cxg9j6dtitgh.us-east-2.rds.amazonaws.com",
    port = 3306,
    user = "admin",
    passwd = "npj3sTTOgk3UhKuCSyof"
)

cursor = conn.cursor()
cursor.execute("USE innodb")
cursor.execute("SELECT * FROM eMessagesTable")

for x in cursor:
    print("=================================================================")
    print(x[0])
    print("=================================================================")
    print("")

print(conn)
conn.commit()
conn.close()
