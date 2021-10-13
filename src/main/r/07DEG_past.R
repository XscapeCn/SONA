deg <- read.csv("D:/Desktop/DEG_LA.csv", header = 1)

consever <- deg[which(deg$log2FoldChange < 2 & deg$log2FoldChange > -2),]


res <- read.table("D:/Desktop/LA_DEG_result.txt")


awnm <- read.table("D:/Desktop/S4AwnGeneInModule.txt")
leafm <- read.table("D:/Desktop/S4LeafGeneInModule.txt")


df <- merge(res,awnm,by =0,all = TRUE)
rownames(df) <- df[,1]
df <- df[,-1]
df <- df[,-8]
colnames(df)[7] <- "awnm"

df <- merge(df,leafm,by =0,all = TRUE)
rownames(df) <- df[,1]
df <- df[,-1]
df <- df[,-9]
colnames(df)[8] <- "leafm"


write.table(df, file="DEG.txt", sep = "\t", quote = F)
plot <- df
plot[which(plot$deg=="Not significant"),9] <- NA
library("ggplot2")
ggplot(data=subset(plot,!is.na(plot$deg)), aes(x=awnm,fill =deg)) +
  geom_bar(position="fill")+
  theme_bw()+scale_fill_viridis(discrete = T) +
  xlim(0.5,72)+
  theme(text = element_text(size = 15))+ scale_fill_brewer(palette="Reds")

df[which(abs(df$log2FoldChange) < 1 & df$padj <= 0.05) ,9 ] <- "Same"
df[which(df$padj > 0.05) ,9 ] <- "Not significant"
df[which(abs(df$log2FoldChange) >= 1 & df$padj <= 0.05) ,9 ] <- "Different"



awnM12 <- row.names(df)[which(df$awnm == 12)]

awnm12Inleaf <- df[awnM12,]
ggplot(data=awnm12Inleaf, aes(x=leafm)) +
  geom_bar(position="stack")+
  theme_bw()+scale_fill_viridis(discrete = T) +
  xlim(0.5,40)+
  theme(text = element_text(size = 15))
