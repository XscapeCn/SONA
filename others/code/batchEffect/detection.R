library('ggplot2')

read <- function(num){
  dataframe <- read.table(paste(num, '_tpm.tsv', sep = ''), header = T)
  # dataframe <- read.table(num, header = T)
  rownames(dataframe) <- dataframe[,1]
  dataframe <- dataframe[,-1]
  return(dataframe)
}

# Data processing
filter <- function(dataframe){
  dataframe <- dataframe[-which(rowSums(dataframe[, 1:ncol(dataframe)] == 0) > ncol(dataframe)*0.2),]
  return(dataframe)
}

## Error occur, do not know the reason
# count_zero <- function(dataframe){
#   for (i in c(1:nrow(dataframe))) {
#     dataframe[i,'count'] <- rowSums(dataframe[i,] == 0)
#   }
#   return(dataframe)
# }

get_Mean <- function(dataframe){
  newFrame <- data.frame(rownames(dataframe), apply(dataframe,1,mean))
  return(newFrame)
}

draw <- function(t1Mean, t2Mean, color = '#848ccf'){
  mergeFrame <- merge(t1Mean, t2Mean, by = 'geneList', all=F)
  mergeFrame[, c('mean.x', 'mean.y')] <- log2(mergeFrame[, c('mean.x', 'mean.y')] + 1)
  
  dat.lm <- lm(mean.x ~ mean.y, data = mergeFrame)
  formula <- sprintf("italic(y) == %.2f %+.2f * italic(x)",
                     round(coef(dat.lm)[1],2),round(coef(dat.lm)[2],2))
  r2 <- sprintf("italic(R^2) == %.2f",summary(dat.lm)$r.squared)
  labels <- data.frame(formula = formula, r2 = r2, stringsAsFactors = FALSE)
  
  p <- ggplot(mergeFrame,aes(x=mean.x,y=mean.y)) + geom_point(size = 1, colour = color, alpha = 1/10) +
    xlab(filename1) + ylab(filename2)
  p <- p + geom_abline(intercept = coef(dat.lm)[1],slope = coef(dat.lm)[2], size = 1) +
    geom_text(data=labels, mapping=aes(x = 2, y=12, label=formula), parse = TRUE, inherit.aes = FALSE, size = 6) + 
    geom_text(data=labels, mapping=aes(x = 2, y=11, label=r2), parse = TRUE, inherit.aes = FALSE, size = 6)
  return(p)
}

save <- function(p, w = 1000, h = 800){
  png(paste(filename1, '-', filename2, '.png', sep = ''), width = w, height = h, res = 150)
  print(p)
  dev.off()
}

arg <- commandArgs(trailingOnly = TRUE)
filename1 <- arg[1]
filename2 <- arg[2]
t1 <- read(filename1)
t2 <- read(filename2)

t1_fil <- filter(t1)
t2_fil <- filter(t2)

t1Mean <- get_Mean(t1_fil)
t2Mean <- get_Mean(t2_fil)

p <- draw(t1Mean, t2Mean)
save(p)
