#!/usr/bin/Rscript
library(GeneOverlap)
args <- commandArgs(trailingOnly = TRUE)
c1 <- args[1]
c2 <- args[2]
dic<- args[3]

# if(!endsWith(dic, "/")){
#   dic <- paste(dic , "/", sep = "")
# }

dir.create(dic)

c1 <- "D:/Desktop/S4StemGeneInModule.txt"
c2 <- "D:/Desktop/S4LeafGeneInModule.txt"
dic <- "H:/Rtest/stemVsLeaf/"

t1 <- read.table(c1)
t2 <- read.table(c2)
t1$X <- rownames(t1)
t2$X <- rownames(t2)

modules <- merge(t1, t2, by ="X", suffixes = c(".t1",".t2"))

modules <- modules[,c(1,2,4)]

write.table(modules, file = paste(dic, "ModuleGene.txt", sep = ""), sep = "\t", quote = F)


background_size <- nrow(modules) # need the number of genes in the background

t1List <- list()
for (i in 0:(length(unique(modules$bwnetModuleLabels.t1))-1)) {
  # print(i)
  t1List[[i+1]] <- assign(paste0("t1",i), modules[modules$bwnetModuleLabels.t1 ==i,][,1])
}

names(t1List) <- c(paste0("row",0:(length(unique(modules$bwnetModuleLabels.t1))-1)))
sapply(t1List,length) # check lengths of list

t2List <- list()
for (i in 0:(length(unique(modules$bwnetModuleLabels.t2))-1)) {
  # print(i)
  t2List[[i+1]] <- assign(paste0("t2",i), modules[modules$bwnetModuleLabels.t2 ==i,][,1])
}
names(t2List) <- c(paste0("col",0:(length(unique(modules$bwnetModuleLabels.t2))-1)))
sapply(t2List,length) # check lengths of list

# now do root vs leaf comparison
# now do for both lists against both lists
gom.obj <- newGOM(t1List, t2List, background_size)

pvalue_matrix <- getMatrix(gom.obj, name="pval") # get pvalue as a table
Jaccard_matrix <- getMatrix(gom.obj, name="Jaccard") # get Jaccard as table

# we can also plot showing the Jaccard index as the colour
# pdf(file="root_embryo_module_comparison_fisher_test_coloured_by_Jaccard.pdf", width=50, height=50)
# drawHeatmap(gom.obj, what="Jaccard", ncolused = 5, grid.col = "Blues", note.col = "black")
# dev.off()

write.table(file=paste(dic, "pvalues_matrix67.txt", sep = ""), pvalue_matrix, sep = "\t", quote = F )
write.table(file=paste(dic, "Jaccard_matrix67.txt", sep = ""), Jaccard_matrix, sep = "\t", quote = F )
## now want to FDR adjust the pvalues


#root_embyro
pvalue_root_embyro <- read.csv(file="pvalues_matrix_root_embryo.csv")
# pvalue_root_embyro[1:4,1:4]
rownames(pvalue_matrix) <- pvalue_root_embyro$X
pvalue_root_embyro <- pvalue_root_embyro[,-1]
# pvalue_root_embyro[1:4,1:4]
pvalue_matrix_root_embyro <- as.matrix(pvalue_root_embyro)
# pvalue_matrix_spike_root[1:4,1:4]
dim(pvalue_matrix_root_embyro)

padj_matrix_root_embyro <- p.adjust(pvalue_matrix_root_embyro, method= "BY")
length(padj_matrix_root_embyro)
padj_matrix_root_embyro <- matrix(padj_matrix_root_embyro, ncol=ncol(pvalue_matrix_root_embyro))
# padj_matrix_root_embyro[1:4,1:4]
padj_matrix_root_embyro <- as.data.frame(padj_matrix_root_embyro)
colnames(padj_matrix_root_embyro) <- colnames(pvalue_matrix_root_embyro)
rownames(padj_matrix_root_embyro) <- rownames(pvalue_root_embyro)
# padj_matrix_spike_root[1:4,1:4]

write.csv(file="padj_matrix_root_embyro.csv",padj_matrix_root_embyro)