site="https://mirrors.tuna.tsinghua.edu.cn/CRAN"
install.packages("BiocManager")
install.packages(c("WGCNA", "stringr", "reshape2", 'ggplot', 'ggplot2', 'knitr', 'limma', 'RcolorBrew'), repos=site)

BiocManager::install(c("AnnotationHub", "biomaRt","clusterProfiler", "topGO", "Rgraphviz", "pathview"，
"AnnotationDbi", "impute","GO.db", "preprocessCore", 'DESeq2'))

library('AnnotationHub')
library('biomaRt')
library('topGO')
library('Rgraphviz')
library('pathview')
library('clusterProfiler')
library('biomaRt')
library('ggplots')
library('ggplot2')
library('knitr')
library('limma')
library('reshape2')
library('RColorBrewer')
library('WGCNA')
library('stringr')
library('DESeq2')

query(hub, 'Triticum')
# AH72161 | org.Triticum_aegilops.eg.sqlite
# AH72162 | org.Triticum_tauschii.eg.sqlite
Triticum.OrgDb <- hub[['AH72162']]

columns(Triticum.OrgDb)

listMarts(host="plants.ensembl.org")
ensembl <- useMart(biomart = "plants_mart",host = "plants.ensembl.org")
# listDatasets(ensembl) 
mart <- useDataset("taestivum_eg_gene", mart = useMart(biomart = "plants_mart",host = "plants.ensembl.org"))

transform <- function(modulename, attributes_to) {
  color <- colnames(datExpr)[moduleColors1 == modulename]
  color_go_term <- getBM(filters="ensembl_gene_id",
                         attributes=c("ensembl_gene_id", attributes_to),
                         values=color,
                         mart= mart)
  color_go_term <- color_go_term[-which(color_go_term[,2] == ''), ]
  return(color_go_term)
}

enrich_self <- function(genelist, ont, pvalueCutoff = 0.01, qvalueCutoff = 0.06) {
  enrich <- enrichGO(gene = genelist,
                     OrgDb = Triticum.OrgDb,
                     keyType = "GO",
                     ont = ont,
                     pvalueCutoff = pvalueCutoff,
                     qvalueCutoff = qvalueCutoff,
                     readable =T)
  return(enrich)
}

draw <- function(name, yourDraw, width1 = 2560, height1 = 1440, res = 300){
  png(file = name, width = width1, height = height1 , res = res )
  print(yourDraw)
  dev.off()
}

# draw <- function(name, yourDraw, width1 = 1920, height1 = 1080, res = 300){
#   library(stringr)
#   test_is_png <- str_split_fixed(name, '.', 2)[2]
#   if(test_is_png == '.png'){
#     png(file = name, width = width1, height = height1, res = res)
#     print(yourDraw)
#     dev.off
#   }else if(test_is_png == '.pdf'){
#     if(width1 > 100 | height1 > 100){
#       print("Enter a valid Resolution for PDF")
#     }else{
#       pdf(file = name, width = width1, height = height1)
#       print(yourDraw)
#       dev.off
#     }
#   }
# }

# test if these functions work
purple_go_term <- transform('purple', "go_id")
a <- dotplot(enrich_self(purple_go_term[,2], "BP"))
draw('a.png', a)