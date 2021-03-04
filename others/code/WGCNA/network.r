database <- read.table("example.txt", header=T, row.names=1, com='', quote='',
                   check.names=F, sep="\t")

# specific to my own database
# rownames(database)<-database[,1]
# database<-database[,-1]
# database<-database[,-97]
# database <- database[, -which(colnames(database) == 'countmergecol.txt')]

colData = colnames(database)
# colData = colData[-96]
database[is.na(database)] <- 0

dds <- DESeqDataSetFromMatrix(countData = database, colData = as.data.frame(colData), design= ~1)
dds <- estimateSizeFactors(dds)
resexpress<-as.data.frame(counts(dds, normalized=TRUE))
write.table(resexpress,file = "expression.txt",row.names = T)

rawData = read.table("expression.txt")

datExpr0 <- as.data.frame(t(rawData))
gsg = goodSamplesGenes(datExpr0, verbose = 3)
gsg$allOK

sampleTree = hclust(dist(datExpr0), method = "average")

sizeGrWindow(16,9)
par(cex = 0.6);
par(mar = c(0,4,2,0))
plot(sampleTree, main = "Sample clustering to detect outliers", sub="", xlab="", cex.lab = 1.5, 
     cex.axis = 1.5, cex.main = 2)
abline(h = 30000, col = "red")

clust = cutreeStatic(sampleTree, cutHeight = 30000, minSize = 10)
table(clust)
keepSamples = (clust==1)

datExpr = datExpr0[keepSamples, ]
nGenes = ncol(datExpr)
nSamples = nrow(datExpr)

sampleTree2 = hclust(dist(datExpr), method = "average")
traitColors = numbers2colors(datExpr, signed = FALSE)
plotDendroAndColors(sampleTree2,traitColors,
                    groupLabels = names(datExpr), 
                    main = "Sample dendrogram and trait heatmap")

powers = c(c(1:10), seq(from = 12, to=20, by=2))
sft = pickSoftThreshold(datExpr, powerVector = powers, verbose = 5)
sizeGrWindow(9, 5)
par(mfrow = c(1,2));
cex1 = 0.9;
plot(sft$fitIndices[,1], -sign(sft$fitIndices[,3])*sft$fitIndices[,2],ylim=c(-0.5, 1),
     xlab="Soft Threshold (power)",ylab="Scale Free Topology Model Fit,signed R^2",type="n",
     main = paste("Scale independence"))
text(sft$fitIndices[,1], -sign(sft$fitIndices[,3])*sft$fitIndices[,2],
     labels=powers,cex=cex1,col="red");
abline(h=0.90,col="red")
plot(sft$fitIndices[,1], sft$fitIndices[,5],
     xlab="Soft Threshold (power)",ylab="Mean Connectivity", type="n",
     main = paste("Mean connectivity"))
text(sft$fitIndices[,1], sft$fitIndices[,5], labels=powers, cex=cex1,col="red")

net1 = blockwiseModules(datExpr, maxBlockSize = 10000, power = 3,
                        TOMType = 'unsigned',
                        minModuleSize = 100, 
                        reassignThreshold = 0, 
                        mergeCutHeight = .25,
                        numericLabels = TRUE, 
                        saveTOMs = FALSE, 
                        verbose = 3)

plotDendroAndColors(net1$dendrograms[[1]], mergedColors[net1$blockGenes[[1]]],
                    "Module colors",
                    dendroLabels = FALSE, hang = 0.03,
                    addGuide = TRUE, guideHang = 0.05)

moduleLabels1 = net1$colors
moduleColors1 = labels2colors(net1$colors)

HubGenes <- chooseTopHubInEachModule(datExpr,moduleColors1)
write.csv(HubGenes,file = "HubGenes_of_each_module.csv", row.names = FALSE)

MEs = moduleEigengenes(selecteddatExpr, selectedmodule)$eigengenes

large <- table(moduleColors1)
large <- as.data.frame(large)
large <- large[-which(large[,2] < 1000), ]
large <- large[-which(large[,1] == "grey"), ]
large1000 <- large$moduleColors1
selectedmodule <- moduleColors1[moduleColors1 %in% large1000]
selecteddatExpr <- datExpr[, c(1:107891)[moduleColors1 %in% large1000]]

MET = orderMEs(MEs)
par(cex = 1.0)
plotEigengeneNetworks(MET, "Eigengene adjacency heatmap", marHeatmap = c(3,4,2,2),
                      plotDendrograms = FALSE, xLabelsAngle = 90)