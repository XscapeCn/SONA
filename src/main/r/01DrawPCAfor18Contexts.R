install.packages(c("ggpubr", "ggthemes", "gmodels"))
install.packages("Rtsne")
library('Rtsne')
library("ggpubr")
library("ggthemes")
library("gmodels")
library("dplyr")

filter <- function(dataframe){
  # dataframe[which(rowSums(is.na(dataframe)) > ncol(dataframe)*0.2),] <- NA
  dataframe[which(rowSums(dataframe == 0) > ncol(dataframe)*0.2),] <- NA
  return(dataframe)
}

drop_Nas <- function (data,dim =1){
  if (dim == 2){
    na_flag <- apply(!is.na(data),2,sum) # use ! to inverse 1 (to 0 0) and 0 (to 1).
    data <- data[,-which(na_flag == 0)]
  }
  else if (dim == 1){
    na_flag <- apply(!is.na(data),1,sum)
    data <- data[-which(na_flag == 0),]
  }
  else{
    warning("dim can only equal to 1 and 2, 1 means row, 2 means column ")
  }
  return(data)
}

get_PCA <- function(file, type = "leaf", stage = "S1"){
  stem <- read.table(file)
  rownames(stem) <- stem[,4]
  stem <- stem[,-c(1:4)] %>% filter() %>% drop_Nas()
  pca.info.stem <- fast.prcomp(stem)
  pca.data.stem <- as.data.frame(sample = rownames(pca.info.stem$rotation), pca.info.stem$rotation)
  pca.data.stem <- pca.data.stem[, c(1:3)]
  pca.data.stem[,"Type"] = type
  pca.data.stem[,"Stage"] = stage
  # pca.data.stem[,"Gene"] = rownames(pca.data.stem)
  rownames(pca.data.stem) <- c(1:nrow(pca.data.stem))
  return(pca.data.stem)
}

aa <- get_PCA("D:/Desktop/S4leafexpression_hapscanner_donor02.txt", "leaf","S4")
bb <- get_PCA("D:/Desktop/S4stemexpression_hapscanner_donor02.txt", "stem","S4")
cc <- get_PCA("D:/Desktop/S4Awnexpression_hapscanner_donor02.txt", "awn","S4")

df <- rbind(aa,bb,cc)
df$Stage <- as.factor(df$Stage)
df$Type <- as.factor(df$Type)
ggscatter(df, x = "PC1", y= "PC2", color="Type", shape = "Stage", alpha = 0.1, size = 2, ellipse = TRUE)
ggscatter(df, x = "PC1", y= "PC2", color="Type", alpha = 0.3, size = 2, palette = c("#ffa940","#4c9d4c","#c7b8ff")) 
ggsave("pca_sample.png", width =  5, height = 5, dpi = 300)





















leaf <- read.table("D:/Desktop/S4leafexpression_hapscanner_donor02.txt")
leaf <- leaf[,-c(1:3)]
rownames(leaf) <- leaf[,1]
leaf <- leaf[,-1] 
leaf <- t(leaf)

pca.info <- fast.prcomp(leaf)
pca.data <- as.data.frame(sample = rownames(pca.info$rotation), pca.info$rotation)
pca.leaf.data <- pca.data[,c(1:3)]
pca.leaf.data[, "Type"] =1
pca.leaf.data[,"Stage"] = 1
pca.data.leaf <- pca.leaf.data
# pca.data.leaf[,"Gene"] = rownames(pca.data.leaf)
rownames(pca.data.leaf) <- c(1:nrow(pca.data.leaf))


stem <- read.table("D:/Desktop/S4stemexpression_hapscanner_donor02.txt")
rownames(stem) <- stem[,4]
stem <- stem[,-c(1:4)] %>% filter() %>% drop_Nas() %>%  t()
pca.info.stem <- fast.prcomp(stem)
pca.data.stem <- as.data.frame(sample = rownames(pca.info.stem$rotation), pca.info.stem$rotation)
pca.data.stem <- pca.data.stem[, c(1:3)]
pca.data.stem[,"Type"] = 1
pca.data.stem[,"Stage"] = 2
# pca.data.stem[,"Gene"] = rownames(pca.data.stem)
rownames(pca.data.stem) <- c(1:nrow(pca.data.stem))


awn <- read.table("D:/Desktop/S4Awnexpression_hapscanner_donor02.txt")
rownames(awn) <- awn[,4]
awn <- awn[,-c(1:4)] %>% t()
pca.info.awn <- fast.prcomp(awn)
pca.data.awn <- as.data.frame(sample = rownames(pca.info.awn$rotation), pca.info.awn$rotation)
pca.data.awn <- pca.data.awn[, c(1:3)]
pca.data.awn[,"Type"] = 2
pca.data.awn[,"Stage"] = 1
# pca.data.awn[,"Gene"] = rownames(pca.data.awn)
rownames(pca.data.awn) <- c(1:nrow(pca.data.awn))

df <- rbind(pca.data.leaf, pca.data.awn, pca.data.stem)
df <- rbind(pca.data.leaf, pca.data.awn)
df <- NULL


pca.info <- fast.prcomp(leaf)
pca.data <- as.data.frame(sample = rownames(pca.info$rotation), pca.info$rotation)


