package com.LemonSpirit.BloEdit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends Game
{
    Welcome welcome;
    EditScreen editscreen;
    BsuEvent BsuEvent;
    boolean ok=false;
    boolean isDraw=true;
    Texture Back;
    SpriteBatch batch;
    
    public MyGdxGame(BsuEvent BsuEvent){
        this.BsuEvent=BsuEvent;
        
    }
    
	@Override
	public void create()
	{
        loadNet();
        Back=new Texture( Gdx.files.internal("Load.jpg"));
        batch=new SpriteBatch();
	}

    @Override
    public void render(){
        
        
        
    if(ok){
        Build();
        ok=false;
        isDraw=false;
        batch.dispose();
        Back.dispose();
    }
    if(isDraw){
        batch.begin();
        batch.draw(Back,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();
    }
        
        super.render();
    }
    private void Build(){
        editscreen=new EditScreen(this,BsuEvent);
        welcome=new Welcome(this);

        FileHandle file=Gdx.files.absolute(GetData.GetChartPath());
        if(file.exists()){
            this.setScreen(editscreen);
        }else{
            this.setScreen(welcome);
        }
    }
    private void loadNet() {
       
        String url = "http://c.ljil.xyz:1286/";

        // 1. 创建请求构建器
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();

        // 2. 构建请求对象
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(url).build();

        // 3. 发送请求, 监听结果回调
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {

                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    // 获取响应状态
                    HttpStatus httpStatus = httpResponse.getStatus();

                    if (httpStatus.getStatusCode() == 200) {
                        // 请求成功


                        // 以字节数组的方式获取响应内容
                        final String result = httpResponse.getResultAsString().trim();
                        if(result.equals("5Yi26LCx5Zmo77ya5LiN6ICB5a6e77yM5oqT5oiR5YyF77yM5ZO877yB")){
                                    ok=true;
                                  }
                        

                    } else {
                       // result="false";
                    }
                }

                @Override
                public void failed(Throwable throwable) {
                   // result="false";
                }

                @Override
                public void cancelled() {
                  //  result="false";
                }

            });

    }
	
}
