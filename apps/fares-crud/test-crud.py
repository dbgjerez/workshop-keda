import requests
import json
import colorama
from colorama import Fore, Style

scheme="http"
host="localhost"
port=8080
path="/fares"
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
payload = {'vehicleType': '3', 'minutePrice': 0.02}
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
newMinutePrice=0.03
obj["minutePrice"]=newMinutePrice
r = requests.put(url, json.dumps(obj))
msg = "Updating price per minute: {}".format(r.status_code)
printResult(msg, 200, r.status_code)
id=str(res[0]["id"])
r = requests.get(url+"/"+id)
obj = r.json()
minutePriceReceived=obj["minutePrice"]
printResult("Expected minute price {} and received {}".format(newMinutePrice, minutePriceReceived),
             newMinutePrice, 
             minutePriceReceived)

res = getAllElements(url)

deleteAllElements(url, res)