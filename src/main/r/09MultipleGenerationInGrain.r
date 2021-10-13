#!/usr/bin/Rscript
library("dplyr")
library("WGCNA")
library("ggplot2")

s6 <- read.table("../../Data/Expression/S6grain_nor.txt", row.names = 1, header=1) %>% .[,-1] %>% t() 
s7 <- read.table("../../Data/Expression/S7grain_nor.txt", row.names = 1, header=1) %>% .[,-1] %>% t() 
s8 <- read.table("../../Data/Expression/S8grain_nor.txt", row.names = 1, header=1) %>% .[,-1] %>% t() 
s9 <- read.table("../../Data/Expression/S9grain_nor.txt", row.names = 1, header=1) %>% .[,-1] %>% t() 

myNet <- function(datExpr){
    gsg <- goodSamplesGenes(datExpr, verbose = 3)
    gsg$allOK
    powers = c(c(1:10), seq(from = 12, to=20, by=2))
    sft = pickSoftThreshold(datExpr, powerVector = powers, verbose = 5)
    aaa <- sft$fitIndices
    aaa$scale <- -sign(aaa[,3]) * aaa[,2]
    ratio = max(aaa$mean.k.)
    bwnet = blockwiseModules(datExpr, maxBlockSize = 60000, power = sft$powerEstimate,
                         TOMType = "unsigned", minModuleSize = 30,
                         reassignThreshold = 0, mergeCutHeight = 0.25,
                         numericLabels = TRUE, pamRespectsDendro = FALSE,
                         saveTOMs = FALSE,
                         verbose = 3)
    return(bwnet)
}

bwnet6 <- myNet(s6)
bwnet7 <- myNet(s7)
bwnet8 <- myNet(s8)
bwnet9 <- myNet(s9)

bwnetModuleColors <- labels2colors(bwnet$colors)
bwnetModuleLabels <- bwnet$colors
HubGenes <- chooseTopHubInEachModule(datExpr,bwnetModuleColors)
write.table(HubGenes,"HubGenes.txt", row.names = F, quote = F)
write.table(table(bwnetModuleLabels), "BwnetModuleTable.txt", sep = "\t", row.names = F,quote = F)
write.table(cbind(bwnetModuleLabels, bwnetModuleColors), "GeneInModule.txt", sep = "\t", quote = F)

bwnetModuleColors6 <- labels2colors(bwnet6$colors)
bwnetModuleLabels6 <- bwnet6$colors
HubGenes6 <- chooseTopHubInEachModule(s6,bwnetModuleColors6)
write.table(HubGenes6,"HubGenes6.txt", row.names = F, quote = F)
write.table(table(bwnetModuleLabels6), "BwnetModuleTable6.txt", sep = "\t", row.names = F,quote = F)
write.table(cbind(bwnetModuleLabels6, bwnetModuleColors6), "GeneInModule6.txt", sep = "\t", quote = F)

bwnetModuleColors7 <- labels2colors(bwnet7$colors)
bwnetModuleLabels7 <- bwnet7$colors
HubGenes7 <- chooseTopHubInEachModule(s7,bwnetModuleColors7)
write.table(HubGenes7,"HubGenes7.txt", row.names = F, quote = F)
write.table(table(bwnetModuleLabels7), "BwnetModuleTable7.txt", sep = "\t", row.names = F,quote = F)
write.table(cbind(bwnetModuleLabels7, bwnetModuleColors7), "GeneInModule7.txt", sep = "\t", quote = F)

bwnetModuleColors8 <- labels2colors(bwnet8$colors)
bwnetModuleLabels8 <- bwnet8$colors
HubGenes8 <- chooseTopHubInEachModule(s8,bwnetModuleColors8)
write.table(HubGenes8,"HubGenes8.txt", row.names = F, quote = F)
write.table(table(bwnetModuleLabels8), "BwnetModuleTable8.txt", sep = "\t", row.names = F,quote = F)
write.table(cbind(bwnetModuleLabels8, bwnetModuleColors8), "GeneInModule8.txt", sep = "\t", quote = F)

savehistory()
save.image()




#####Below is test

python3 -m tensorqtl ./drive/MyDrive/2021testTensor/bed/38 ./drive/MyDrive/2021testTensor/38.bed.gz ./drive/MyDrive/2021testTensor/1/ --covariates ./drive/MyDrive/2021testTensor/10_cov.txt.gz --mode cis



print(commandArgs(trailingOnly = TRUE)[1])
args <- commandArgs(trailingOnly = TRUE)[1]

dir <- paste(as.character(strsplit(args,split='[.]')[[1]][1]), "/" ,sep = "")
dir.create(dir)
context <- strsplit(dir[[1]][1],split='/')
context <- context[[1]][length(context[[1]])]

datExpr <- read.table(args, row.names = 1) %>% .[,-1] %>% t() 

gsg <- goodSamplesGenes(datExpr, verbose = 3)
gsg$allOK

powers = c(c(1:10), seq(from = 12, to=20, by=2))
sft = pickSoftThreshold(datExpr, powerVector = powers, verbose = 5)

aaa <- sft$fitIndices
aaa$scale <- -sign(aaa[,3]) * aaa[,2]
ratio = max(aaa$mean.k.)

p <- ggplot(aaa) + geom_line(aes(Power, scale), size = 2, color = "#52006A")+ 
  geom_vline(xintercept = aaa$Power, alpha=0.3, color = "grey")+
  geom_hline(yintercept = 0.8, alpha=0.7, color = "red",linetype="dashed")+
  annotate("segment", x = 14, xend = 14.5, y = 0.6, yend = 0.6, size = 2,color ="#52006A")+
  annotate("text", x = 16.3, y = 0.6, size =4,label = "Scale free Topology")+
  annotate("segment", x = 14, xend = 14.5, y = 0.55, yend = 0.55, size = 2,color ="#CD113B")+
  annotate("text", x = 15.7, y = 0.55, size =4,label = "Connectivity")+
  theme_bw() + 
  theme(text = element_text(size =18)) + 
  labs(y = "Scale Free Topology Model Fit,signed R^2", title = "Scale-free and Connectivity trade-off in S4Leaf")+
  geom_line(aes(Power, mean.k./ratio) ,color = "#CD113B", size = 2)+
  theme(panel.grid.major=element_blank(),panel.grid.minor=element_blank())
p + scale_y_continuous( limits = c(0,1), breaks = c(0.2,0.4,0.6,0.8,1.0), sec.axis = sec_axis(~.*ratio, name ="Mean Connectivity"))+
  scale_x_continuous(breaks = c(seq(1,20,1)))
ggsave(paste(dir, context, "Trade.pdf", sep =""), height = 6, width = 8, dpi = 300)

bwnet = blockwiseModules(datExpr, maxBlockSize = 60000, power = sft$powerEstimate,
                         TOMType = "unsigned", minModuleSize = 30,
                         reassignThreshold = 0, mergeCutHeight = 0.25,
                         numericLabels = TRUE, pamRespectsDendro = FALSE,
                         saveTOMs = FALSE,
                         verbose = 3)


bwnetModuleColors <- labels2colors(bwnet$colors)
bwnetModuleLabels <- bwnet$colors

HubGenes <- chooseTopHubInEachModule(datExpr,bwnetModuleColors)
write.table(HubGenes,file = paste(dir , context, "HubGenes.txt", sep = ""), row.names = F, quote = F)
write.table(table(bwnetModuleLabels), file=paste(dir, context, "BwnetModuleTable.txt",sep = ""), sep = "\t", row.names = F,quote = F)
bwnetMEs <- bwnet$MEs
bwnetdendrograms <- bwnet$dendrograms
write.table(cbind(t(datExpr),bwnetModuleLabels, bwnetModuleColors), paste(dir, context,"GeneExpInModule.txt", sep =""), sep = "\t", quote = F)
write.table(cbind(bwnetModuleLabels, bwnetModuleColors), paste(dir, context, "GeneInModule.txt", sep =""), sep = "\t", quote = F)