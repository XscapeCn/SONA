#!/usr/bin/Rscript
library("dplyr")
library("WGCNA")
library("ggplot2")

# filter <- function(dataframe){
#   # dataframe[which(rowSums(is.na(dataframe)) > ncol(dataframe)*0.2),] <- NA
#   dataframe[which(rowSums(dataframe ==0) > ncol(dataframe)*0.8),] <- NA
#   return(dataframe)
# }
# 
# drop_Nas <- function (data,dim =1){
#   if (dim == 2){
#     na_flag <- apply(!is.na(data),2,sum) # use ! to inverse 1 (to 0 0) and 0 (to 1).
#     data <- data[,-which(na_flag == 0)]
#   }
#   else if (dim == 1){
#     na_flag <- apply(!is.na(data),1,sum)
#     data <- data[-which(na_flag == 0),]
#   }
#   else{
#     warning("dim can only equal to 1 and 2, 1 means row, 2 means column ")
#   }
#   return(data)
# }
print(commandArgs(trailingOnly = TRUE)[1])
args <- commandArgs(trailingOnly = TRUE)[1]

dir <- paste(as.character(strsplit(args,split='[.]')[[1]][1]), "/" ,sep = "")
dir.create(dir)
context <- strsplit(dir[[1]][1],split='/')
context <- context[[1]][length(context[[1]])]

datExpr <- read.table(args, row.names = 4) %>% .[,-c(1:3)] %>% t() 

gsg <- goodSamplesGenes(datExpr, verbose = 3)
gsg$allOK

powers = c(c(1:10), seq(from = 12, to=20, by=2))
sft = pickSoftThreshold(datExpr, powerVector = powers, verbose = 5)

# sizeGrWindow(16, 9)
# pdf(file=paste(dir, context, "TradeOff.pdf", sep = ""), width = 8, height = 4.5)
# par(mfrow = c(1,2));
# cex1 = 0.9;
# plot(sft$fitIndices[,1], -sign(sft$fitIndices[,3])*sft$fitIndices[,2],
#      xlab="Soft Threshold (power)",ylab="Scale Free Topology Model Fit,signed R^2",type="n",
#      main = paste("Scale independence"));
# text(sft$fitIndices[,1], -sign(sft$fitIndices[,3])*sft$fitIndices[,2],
#      labels=powers,cex=cex1,col="red");
# abline(h=0.90,col="red")
# plot(sft$fitIndices[,1], sft$fitIndices[,5],
#      xlab="Soft Threshold (power)",ylab="Mean Connectivity", type="n",
#      main = paste("Mean connectivity"))
# text(sft$fitIndices[,1], sft$fitIndices[,5], labels=powers, cex=cex1,col="red")
# dev.off()

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

