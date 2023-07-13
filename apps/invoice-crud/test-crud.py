import requests
import json
import colorama
from colorama import Fore, Style

scheme="http"
host="localhost"
port=8080
path="/invoice"
url=scheme+"://"+host+":"+str(port)+path

def getAllElements(url):
    r = requests.get(url)
    res = r.json()
    print("Total elements: ", str(len(res)))
    return res

def printResult(msg, expected, result):
    if expected == result:
        printTestCorrect(msg)
    else:
        printTestFail(msg)

def printTestCorrect(msg):
    printTest(msg, Fore.GREEN)

def printTestFail(msg):
    printTest(msg, Fore.RED)

def printTest(msg, colour):
    print(f"{colour}",msg,f"{Style.RESET_ALL}")

def deleteAllElements(url, res):
    for p in res:
        r = requests.delete(url+"/"+str(p["id"]))
        msg = "Deleting object with id: {} with result {}".format(p["id"], str(r.status_code))
        printResult(msg, 200, r.status_code)


res = getAllElements(url)

deleteAllElements(url, res)

print("TESTING CREATE")
payload = {'plate': '0000GGG', 'amount': 12.30}
r = requests.post(url, json.dumps(payload))
msg = "Creating first element: {}".format(r.status_code)
printResult(msg, 200, r.status_code)
res = getAllElements(url)

print("TESTING GET")
id=str(res[0]["id"])
r = requests.get(url+"/"+id)
msg = "Get created element [id={}]: {}".format(id,r.status_code)
printResult(msg, 200, r.status_code)
obj = r.json()

print("TESTING UPDATE PLATE")
newPlate="0001HHH"
obj["plate"]="0001HHH"
r = requests.put(url, json.dumps(obj))
msg = "Updating plate: {}".format(r.status_code)
printResult(msg, 200, r.status_code)
id=str(res[0]["id"])
r = requests.get(url+"/"+id)
obj = r.json()
plateReceived=obj["plate"]
printResult("Expected plate {} and received {}".format(newPlate, plateReceived),
             newPlate, 
             plateReceived)

invoiceDate = "2023-07-05T15:59:00Z"
obj["invoiceDate"]=invoiceDate
r = requests.put(url, json.dumps(obj))
msg = "Updating invoiceDate: {}".format(r.status_code)
printResult(msg, 200, r.status_code)
id=str(res[0]["id"])
r = requests.get(url+"/"+id)
obj = r.json()
invoiceDateReceived=obj["invoiceDate"]
printResult("Expected entrance date {} and received {}".format(invoiceDate, invoiceDateReceived),
             invoiceDate, 
             invoiceDateReceived)

res = getAllElements(url)

deleteAllElements(url, res)