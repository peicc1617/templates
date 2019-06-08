package cn.edu.xjtu.cad.templates.model.project.node;

/**
 * 该类表示延期类型，采用传统的静态三分法。
 */
public enum DateDelay {
    N,//还没有开始执行
    S,//没啥问题
    W,//warining
    D//danger
}
