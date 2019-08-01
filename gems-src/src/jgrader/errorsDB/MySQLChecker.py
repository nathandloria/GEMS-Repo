import mysql.connector as mc

conn = mc.connect(
    host = "gemserrors.cxg9j6dtitgh.us-east-2.rds.amazonaws.com",
    port = 3306,
    user = "gems_user",
    passwd = "9daR1DjdQbSwo19HCMqj"
)

cursor = conn.cursor()
cursor.execute("USE innodb")
cursor.execute("SELECT * FROM gems_error_messages")

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
