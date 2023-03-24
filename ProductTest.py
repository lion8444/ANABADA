import nltk
from nltk.tokenize import word_tokenize
from nltk.tag import pos_tag
from nltk.chunk import ne_chunk

nltk.download()

# Example sentence
sentence = "I love my Apple iPhone and Samsung Galaxy. Their cameras are amazing!"

# Tokenize the sentence
tokens = word_tokenize(sentence)

# Part of speech tagging
pos_tags = pos_tag(tokens)

# Chunking
chunks = ne_chunk(pos_tags)

# Named entity recognition
for chunk in chunks:
    if hasattr(chunk, 'label') and chunk.label() == 'ORG':
        print('Product name:', ' '.join(c[0] for c in chunk))
