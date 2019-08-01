import mysql.connector as mc
import sys

conn = mc.connect(
    host = "gemserrors.cxg9j6dtitgh.us-east-2.rds.amazonaws.com",
    port = 3306,
    user = "gems_user",
    passwd = "9daR1DjdQbSwo19HCMqj"
)

ogerrorsarr = []
eerrorsarr = []

filepath = r'/home/lorian/Desktop/GEMS/GEMS-Repo/gems-src/src/jgrader/errorsDB/enhancederrors.txt'
with open(filepath) as fp:
    ecnt = 0
    i = 0
    for line in fp:
        if line[(len(line) - 1):len(line)] == "\n":
            eerrorsarr.append(line[0:(len(line) - 1)])
        else:
            eerrorsarr.append(line[0:len(line)])
        i += 1
        ecnt += 1

filepath = r'/home/lorian/Desktop/GEMS/GEMS-Repo/gems-src/src/jgrader/errorsDB/ogerrors.txt'
with open(filepath) as fp:
    ogcnt = 0
    i = 0
    for line in fp:
        if line[(len(line) - 1):len(line)] == "\n":
            ogerrorsarr.append(line[0:(len(line) - 1)])
        else:
            ogerrorsarr.append(line[0:len(line)])
        i += 1
        ogcnt += 1
v = 0
cursor = conn.cursor()
cursor.execute("USE innodb")
yn = input("Would you like to delete the current data from the database? (y/n): ")
if yn == "y":
    cursor.execute("DELETE FROM gems_error_messages")
if ogcnt == ecnt:
    while v < ogcnt:
        data = {
            'ogError': ogerrorsarr[v],
            'eError': eerrorsarr[v]
        }
        cursor.execute('''INSERT INTO gems_error_messages (ogerrors, eerrors) VALUES (%(ogError)s, %(eError)s)''', data)
        v += 1
else:
    sys.exit("Error with input files! Files are not the same lenth!")
#cursor.execute('''INSERT INTO eMessages (ogErrors, eErrors) VALUES (%(ogError)s, %(eError)s)''', data)
#cursor.execute("SELECT * FROM eMessages")
#cursor.execute("DELETE FROM eMessages")
print("Done! Your database has been updated :)")
print(conn)
conn.commit()
conn.close()
