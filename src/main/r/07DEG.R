library("DESeq2")
library("dplyr")
library("ggplot2")

myReadCT <- function(name){
    s6_ct <- read.table(name,header=1)
    rownames(s6_ct) <- s6_ct[,1]
    s6_ct <- s6_ct[,-1]
}

myDeSeq <- function(s7_ct, s6_ct){
    df_76 <- merge(s7_ct, s6_ct, by = 0, all = T)
    rownames(df_76) <- df_76[,1]
    df_76 <- df_76[,-1]
    group_list_76 <- c(rep("s7", ncol(s7_ct)), rep("s6", ncol(s6_ct))) %>% as.factor()
    colData_76 <- data.frame(row.names=colnames(df_76), group_list=group_list_76)
    dds2_76 <- DESeq(dds_76)
    res_76 <-  results(dds2_76, contrast=c("group_list","s7","s6"))
    return(as.data.frame(res_76))
}

s6_ct <- read.table("../../CountTable/S6grain_countResult.txt",header=1)
s7_ct <- read.table("../../CountTable/S7grain_countResult.txt",header=1)
s8_ct <- read.table("../../CountTable/S8grain_countResult.txt",header=1)
s9_ct <- read.table("../../CountTable/S9grain_countResult.txt",header=1)

rownames(s6_ct) <- s6_ct[,1]
s6_ct <- s6_ct[,-1]
rownames(s7_ct) <- s7_ct[,1]
s7_ct <- s7_ct[,-1]
rownames(s8_ct) <- s8_ct[,1]
s8_ct <- s8_ct[,-1]
rownames(s9_ct) <- s9_ct[,1]
s9_ct <- s9_ct[,-1]

colnames(s6_ct) <- paste(colnames(s6_ct),"s6",sep = "")
colnames(s7_ct) <- paste(colnames(s7_ct),"s7",sep = "")
colnames(s8_ct) <- paste(colnames(s8_ct),"s8",sep = "")
colnames(s9_ct) <- paste(colnames(s9_ct),"s9",sep = "")

df_76 <- merge(s7_ct, s6_ct, by = 0, all = T)
df_87 <- merge(s8_ct, s7_ct, by = 0, all = T)
df_98 <- merge(s9_ct, s8_ct, by = 0, all = T)

rownames(df_76) <- df_76[,1]
df_76 <- df_76[,-1]
rownames(df_87) <- df_87[,1]
df_87 <- df_87[,-1]
rownames(df_98) <- df_98[,1]
df_98 <- df_98[,-1]

group_list_76 <- c(rep("s7", ncol(s7_ct)), rep("s6", ncol(s6_ct))) %>% as.factor()
group_list_87 <- c(rep("s8", ncol(s8_ct)), rep("s7", ncol(s7_ct))) %>% as.factor()
group_list_98 <- c(rep("s9", ncol(s9_ct)), rep("s8", ncol(s8_ct))) %>% as.factor()

# group_list_76 <- c(rep("s7", ncol(s7_ct)), rep("s6", ncol(s6_ct))) 
# group_list_87 <- c(rep("s8", ncol(s8_ct)), rep("s7", ncol(s7_ct))) 
# group_list_98 <- c(rep("s9", ncol(s9_ct)), rep("s8", ncol(s8_ct))) 

colData_76 <- data.frame(row.names=colnames(df_76), group_list=group_list_76)
colData_87 <- data.frame(row.names=colnames(df_87), group_list=group_list_87)
colData_98 <- data.frame(row.names=colnames(df_98), group_list=group_list_98)

dds_76 <- DESeqDataSetFromMatrix(countData = df_76, colData = colData_76, design = ~ group_list)
dds_87 <- DESeqDataSetFromMatrix(countData = df_87, colData = colData_87, design = ~ group_list)
dds_98 <- DESeqDataSetFromMatrix(countData = df_98, colData = colData_98, design = ~ group_list)

dds2_76 <- DESeq(dds_76)
dds2_87 <- DESeq(dds_87)
dds2_98 <- DESeq(dds_98)

res_76 <-  results(dds2_76, contrast=c("group_list","s7","s6"))
res_87 <-  results(dds2_87, contrast=c("group_list","s8","s7"))
res_98 <-  results(dds2_98, contrast=c("group_list","s9","s8"))

write.table(write.table(as.data.frame(res_76), file="76_raw_result.txt", sep = "\t", quote = F))
write.table(write.table(as.data.frame(res_87), file="87_raw_result.txt", sep = "\t", quote = F))
write.table(write.table(as.data.frame(res_98), file="98_raw_result.txt", sep = "\t", quote = F))


dds <- DESeqDataSetFromMatrix(countData = df, colData = colData, design = ~ group_list)
group_list
group_list <- as.factor(group_list)
dds <- DESeqDataSetFromMatrix(countData = df, colData = colData, design = ~ group_list)
colData <- data.frame(row.names=colnames(df), group_list=group_list)
dds <- DESeqDataSetFromMatrix(countData = df, colData = colData, design = ~ group_list)
dds2 <- DESeq2(dds)
library("DESeq2")
dds2 <- DESeq2(dds)
dds2 <- DESeq(dds)
res <-  results(dds2, contrast=c("group_list","leaf","stem"))
write.table(write.table(as.data.frame(res), file="LS_DEG_raw_result.txt", sep = "\t", quote = F))
savehistory()
q()





aa[which(abs(aa$log2FoldChange76) < 1 & aa$padj76 <= 0.05) ,"deg76" ] <- "Same"
aa[which(aa$padj76 > 0.05) ,"deg76"] <- "Not significant"
aa[which(aa$log2FoldChange76 >= 1 & aa$padj76 <= 0.05) ,"deg76" ] <- "Rise"
aa[which(aa$log2FoldChange76 <= -1 & aa$padj76 <= 0.05) ,"deg76" ] <- "Down"


aa[which(abs(aa$log2FoldChange87) < 1 & aa$padj87 <= 0.05) ,"deg87" ] <- "Same"
aa[which(aa$padj87 > 0.05) ,"deg76"] <- "Not significant"
aa[which(aa$log2FoldChange87 >= 1 & aa$padj87 <= 0.05) ,"deg87" ] <- "Rise"
aa[which(aa$log2FoldChange87<= -1 & aa$padj87 <= 0.05) ,"deg87" ] <- "Down"

aa[which(abs(aa$log2FoldChange98) < 1 & aa$padj98 <= 0.05) ,"deg98" ] <- "Same"
aa[which(aa$padj98 > 0.05) ,"deg98"] <- "Not significant"
aa[which(aa$log2FoldChange98 >= 1 & aa$padj98 <= 0.05) ,"deg98" ] <- "Rise"
aa[which(aa$log2FoldChange98<= -1 & aa$padj98 <= 0.05) ,"deg98" ] <- "Down"