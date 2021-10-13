

a <- read.table("H:/Rtest/awnVsLeaf/Jaccard_matrix.txt", header = 1, row.names = 1)
a <- unlist(a)
a <- as.data.frame(a)

a <- a[which(a$a >= 0.1), ]
a <- as.data.frame(a)
a$context <- "awn-leaf"
colnames(a)[1] <- "value"

b <- read.table("H:/Rtest/stemVsLeaf/Jaccard_matrix.txt", header = 1, row.names = 1) %>% unlist() %>% as.data.frame() 
# %>% .[which(.[,1]  >= 0.1),] %>% as.data.frame()
# b <- unlist(b)
# b <- as.data.frame(b)
# b <- b[which(b$b >= 0.1), ]
# b <- as.data.frame(b)
b$context <- "leaf-stem"
colnames(b)[1] <- "value"

c <- read.table("H:/Rtest/awnVsStem/Jaccard_matrix.txt", header = 1, row.names = 1) %>% unlist() %>% as.data.frame() 
c$context <- "awn-stem"
colnames(c)[1] <- "value"
df <- rbind(a,b,c)

ggplot(df) + geom_density(aes(x = value,color = context), size = 1.5)+
  theme_bw()+
  xlim(0.08, 0.6)+
  labs(title = "Jaccard")+
  theme(panel.grid = element_blank(),text = element_text(size = 20))
ggsave("D:/Desktop/ScriptsInNetwork/Figure/Jaccard.pdf", height = 5, width =7, dpi = 300)

# ggplot(b) + geom_density(aes(x = b), size = 2)+
#   theme_bw()+
#   theme(panel.grid = element_blank(),text = element_text(size = 20))

aa <- read.table("H:/Rtest/awnVsLeaf/Jaccard_matrix.txt", header = 1, row.names = 1)
bb <- read.table("H:/Rtest/stemVsLeaf/Jaccard_matrix.txt", header = 1, row.names = 1)
cc <- read.table("H:/Rtest/awnVsStem/Jaccard_matrix.txt", header = 1, row.names = 1)

aaa <- as.data.frame(which(aa >= 0.4, arr.ind = T)) # 42-26
bbb <- as.data.frame(which(bb >= 0.2, arr.ind = T)) # 33-26
ccc <- as.data.frame(which(cc >= 0.2, arr.ind = T)) # 42-33

awngene <- read.table("D:/Desktop/S4AwnGeneInModule.txt")
awngene <- subset(awngene, bwnetModuleLabels == 19)
write.csv(awngene, "D:/Desktop/l.csv",quote = F)

leafgene <- read.table("D:/Desktop/S4LeafGeneInModule.txt")
leafgene <- subset(leafgene, bwnetModuleLabels == 15)

stemgene <- read.table("D:/Desktop/S4StemGeneInModule.txt")
stemgene <- subset(stemgene, bwnetModuleLabels == 33)

share <- intersect(row.names(awngene), intersect(row.names(leafgene),row.names(stemgene)))
write.table(share, "D:/Desktop/aa.txt", row.names = F, quote = F, col.names = F)
