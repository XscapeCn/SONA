#!/usr/bin/Rscript
library(ggplot2)
library(pheatmap)
library(RColorBrewer)
cc = colorRampPalette(c())	
breaks <- seq(0,300,length.out = 100)

args <- commandArgs(trailingOnly = TRUE)
c1 <- args[1]


df <- read.table("D:/Desktop/test/L_Spvalues_matrix.txt", header = 1, row.names = 1)

for (i in c(1:nrow(df))) {
  df[i, which(df[i,] > 0.05 | df[i,] == 0)] <- NA
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

dff <- drop_Nas(df)
dff <- df
dff <- -log10(dff)
dff[is.na(dff)] <- 0

dff <- dff[-1,]
dff <- dff[,-1]
# heatmap(as.matrix(dff))

# pheatmap(dff,cluster_row = FALSE,cluster_cols = F, border_color = "#dadada", show_rownames=F,show_colnames=F,  color = cc(100), breaks = breaks)
pheatmap(dff,cluster_row = FALSE,cluster_cols = F, border_color = "#dadada", show_rownames=F,show_colnames=F,  color = colorRampPalette(c("#EEEBDD", "#1E6F5C", "#FFC93C", "#72147E"))(100), breaks = breaks)
