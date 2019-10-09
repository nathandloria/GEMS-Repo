import mysql.connector as mc
import os
import sys

conn = mc.connect(
    host = "gemserrors.cxg9j6dtitgh.us-east-2.rds.amazonaws.com",
    port = 3306,
    user = "gems_user",
    passwd = "9daR1DjdQbSwo19HCMqj"
)

ogerrorsarr = []
eerrorsarr = []
ogerrorsarrnosort = []

filepath = os.getcwd() + "/enhancederrors.txt"
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

filepath = os.getcwd() + "/ogerrors.txt"
with open(filepath) as fp:
    ogcnt = 0
    i = 0
    for line in fp:
        if line[(len(line) - 1):len(line)] == "\n":
            ogerrorsarr.append(line[0:(len(line) - 1)])
            ogerrorsarrnosort.append(line[0:(len(line) - 1)])
        else:
            ogerrorsarr.append(line[0:len(line)])
            ogerrorsarrnosort.append(line[0:len(line)])
        i += 1
        ogcnt += 1

ogerrorsarr.sort()
eerrorsarrsorted = []

v = 0
k = 0
while v < ogcnt:
    k = 0
    while k < ecnt:
        if ogerrorsarrnosort[k] == ogerrorsarr[v]:
            eerrorsarrsorted.append(eerrorsarr[k])
        k += 1
    v += 1
cursor = conn.cursor()
v = 0
cursor.execute("USE innodb")
yn = input("Would you like to delete the current data from the database? (y/n): ")
if yn == "y":
    cursor.execute("DELETE FROM gems_error_messages")
if ogcnt == ecnt:
    while v < ogcnt:
        data = {
            'ogError': ogerrorsarr[v],
            'eError': eerrorsarrsorted[v]
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
