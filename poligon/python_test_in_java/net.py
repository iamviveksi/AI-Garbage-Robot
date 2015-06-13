#!/usr/bin/python

from fann2 import libfann
import sys
# wczytuje dane ze standardowego wejscia (typ smiecia, nazwa detergentu i nazwa sprzetu oddzielone spacja) i tworzy z nich liste

#a = sys.stdin.readline()
#b = a[:len(a)-1]
#c = b.split(" ")

c = list()

for x in xrange(1,4):
	c.append(sys.argv[x])
	pass


# liczbowa skala dla typow sieci i detergentow
rub = {
"water" : 0.05,
"wine" : 0.1,
"ink" : 0.15,
"blood" : 0.2,
"coffee" : 0.25,
"oil" : 0.3,
"lubricant" : 0.33,
"sauce" : 0.35,
"dressing" : 0.4,
"mud" : 0.45,
"fungus" : 0.5,
"glue" : 0.55,
"paste" : 0.6,
"dust" : 0.65,
"sand" : 0.7,
"cake" : 0.75,
"grass" : 0.8,
"glass" : 0.85,
"paper" : 0.9,
"rot" : 0.95
}

det = {
"nothing" : 0.95,
"water" : 0.80,
"airFreshener" : 0.4,
"cleaningMilk" : 0.5,
"dishSoap" : 0.6,
"floorSoap": 0.6,
"paste": 0.5,
"soap" : 0.7,
"spray" : 0.7,
"washingPowder" : 0.5,
"fumigator" : 0.01,
"alcohol" : 0.02,
"bakingSoda" :  0.4,
"vanish" : 0.4,
"vinegar" : 0.4,
"solvent" : 0.02
}

equ = {
"besom" : 0.8,
"brush" : 0.1,
"grasper" : 0.98,
"pressureWasher" : 0.02,
"rubber" : 0.02,
"sandpaper" : 0.03,
"scraper" : 0.5,
"sponge" : 0.01,
"tweezer" : 0.9,
"vacuumCleaner" : 0.7
}

# zamienia nazwy na przypisane im wartosci liczbowe
rubbish = rub[c[0]]
detergent = det[c[1]]
equipment = equ[c[2]]

# siec wylicza na podstawie wartosci, czy smiec nadaje sie do kazdego z koszy
ann = libfann.neural_net()
ann.create_from_file("xor.net")
res = ann.run([rubbish, detergent, equipment])

# TUTAJ jest problem - ustalam zmienne do zliczania pojemnosci koszy. Te zmnienne powinny zostac ustalone wczesniej, jesli pozostana tutaj, przy kazdym smieciu beda sie zerowac, a przeciez nie o to nam chodzi.
# reusable = 0
# dregs = 0
# infected = 0

# wytypowanie kosza, do ktorego trzeba wrzucic smiec 
trash = ""


if res[2] >= res[0]:
	if res[2] >= res[1]:
		if res[2] >= res[3]:
			trash = "infected"
			# infected = infected + 1
		else:
			trash = "no trash"
	elif res[1] >= res[3]:
		trash = "dregs"
		# dregs = dregs + 1
	else:
		trash = "no trash"
elif res[0] >= res[1]:
	if res[0] >= res[3]:
		trash = "reusable"
		# reusable = reusable + 1
	else:
		trash = "no trash"
elif res[1] >= res[3]:
	trash = "dregs"
	# dregs = dregs + 1
else:
	trash = "no trash"	

# print "\nTrash: "
# sys.stdout.write(trash)
# print "\n\nTrashes: " + "\n" + "reusable: " + str(reusable) + "\n" + "dregs: " + str(dregs) + "\n" + "infected: " + str(infected)
print trash