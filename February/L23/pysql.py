import mysql.connector

# Connection Establishment

conn = mysql.connector.connect(host='localhost',database='feb2026',user='root',password='root')

if conn.is_connected():
    print("Connection Established Successfully")

cursor = conn.cursor()

try:
    cursor.execute("insert into emp values(101,'',96000)")
    conn.commit()
    print('Employee Added Successfully')

except:
    conn.rollback()

cursor.close()
conn.close()
 