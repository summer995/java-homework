'''
Created on 2016.3.26

@author: summer
'''

from __future__ import division
from numpy import *
import operator



def createDataset():
        group=array([[9,400],[200,5],[100,77],[40,300]])
        
        labels=['1','2','3','1']
        return group,labels    
    
def classify(inX, dataSet, labels, k):
    dataSetSize = dataSet.shape[0]
    diffMat = tile(inX, (dataSetSize,1)) - dataSet
    sqDiffMat = diffMat**2
    sqDistances = sqDiffMat.sum(axis=1)
    distances = sqDistances**0.5
    sortedDistIndicies = distances.argsort()
     
    classCount={}          
    for i in range(k):
        voteIlabel = labels[sortedDistIndicies[i]]
        
        classCount[voteIlabel] = classCount.get(voteIlabel,0) + 1
    sortedClassCount = sorted(classCount.iteritems(), key=operator.itemgetter(1), reverse=True)
    return sortedClassCount[0][0]

def autoNorm(dataSet):
    minVals = dataSet.min(0)
    maxVals = dataSet.max(0)
    ranges = maxVals - minVals
    normDataSet = zeros(shape(dataSet))
  
    m = dataSet.shape[0]
    normDataSet = dataSet - tile(minVals, (m,1))
    #print normDataSet
    normDataSet = normDataSet/tile(ranges, (m,1)) #element wise divide
   # print normDataSet
    return normDataSet, ranges, minVals

def result():
    datingDataMat,datingLabels =createDataset()       #load data setfrom file
    normMat, ranges, minVals = autoNorm(datingDataMat)
    book=5
    film=5
    print 'watch book '+str(book)+ ' film '+str(film)
    print 'you are the person belong to'
    print classify((book,film), normMat, datingLabels, 1)
    
    
    
if __name__=='__main__':
    result()      