from konlpy.tag import *
import nltk
import re

#nltk.download()
#nltk.download('punkt')

from nltk.corpus import stopwords 
from nltk.tokenize import word_tokenize, sent_tokenize

hannanum = Hannanum()
okt = Okt()

text = 'iPhone를 Iphone을 Galaxy buy new need purchase 13 프로 삽니다. 필요. 구매합니다.'
text = text.lower()
splitText = text.split(" ")


rdText = []

for value in splitText:
    if value not in rdText:
        rdText.append(value)

new = []

def ProperNounExtractor(text):
    compileEnglish = re.compile('[a-zA-Z]')
    text = list(filter(compileEnglish.match, text))
    
    text_sen = ""
    for i in text:
        text_sen += i + " "
    temp = []
    sentences = nltk.sent_tokenize(text_sen)
    for sentence in sentences:
        words = nltk.word_tokenize(sentence)
        words = [word for word in words if word not in set(stopwords.words('english'))]
        tagged = nltk.pos_tag(words)
        for (word, tag) in tagged:
            if tag == 'NN': # If the word is a proper noun
                temp.append(word)
    return temp            
                
new = ProperNounExtractor(rdText)

for i in text.split(" "):
    if okt.nouns(i) != []:
        new.append(' '.join(str(s) for s in okt.nouns(i)))
    
print(new)


