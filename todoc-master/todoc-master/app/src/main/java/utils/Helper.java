package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.List;

public class Helper extends SQLiteOpenHelper {


    public Helper(@Nullable Context context)
    {
        super(context, "cleanupTodoc", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase dbCleanup) {
      dbCleanup.execSQL("CREATE TABLE task(_id INTEGER PRIMARY KEY,projetId INTEGER,name TEXT," +
              "creationTimestamp DATESTAMP)");
      dbCleanup.execSQL("CREATE TABLE projet(_projetId INTEGER PRIMARY KEY,name TEXT,color TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbCleanup, int i, int i1) {
        dbCleanup.execSQL("DROP TABLE IF EXISTS task");
        dbCleanup.execSQL("DROP TABLE IF EXISTS projet");
        onCreate(dbCleanup);
    }

    public void insertTask (Task task){
        SQLiteDatabase dbCleanup=this.getWritableDatabase();

        ContentValues ts=new ContentValues();

        ts.put("projectId",task.getProject().getId());
        ts.put("name",task.getName());
        ts.put("creationTimestamp",task.getCreationTimestamp());

        dbCleanup.insert("task",null,ts);
        dbCleanup.close();
    }

    public void insertProjet (Project projet){
        SQLiteDatabase dbCleanup=this.getWritableDatabase();

        ContentValues pr=new ContentValues();

        pr.put("name",projet.getName());
        pr.put("color",projet.getColor());

        dbCleanup.insert("projet",null,pr);
        dbCleanup.close();
    }

    public void upDateTask (Task task){
        SQLiteDatabase dbCleanup=this.getWritableDatabase();

        ContentValues ts=new ContentValues();

        ts.put("projectId",task.getProject().getId());
        ts.put("name",task.getName());
        ts.put("creationTimestamp",task.getCreationTimestamp());

        dbCleanup.update("task",ts,"_id=?", new String[]{(String.valueOf(task.getId()))});
        dbCleanup.close();
    }

    public void upDateProjet (Project project){
        SQLiteDatabase dbCleanup=this.getWritableDatabase();

        ContentValues pr=new ContentValues();

        pr.put("name",project.getName());
        pr.put("color",project.getColor());

        dbCleanup.update("projet",pr,"_id=?", new String[]{(String.valueOf(project.getId()))});
        dbCleanup.close();
    }

    public void deleteTask(int id){
        SQLiteDatabase dbCleanup=this.getWritableDatabase();

        dbCleanup.delete("task","_id",new String[]{(String.valueOf(id))});
        dbCleanup.close();

    }

    public void deleteProjet(int id){
        SQLiteDatabase dbCleanup=this.getReadableDatabase();

        dbCleanup.delete("project","_id",new String[]{(String.valueOf(id))});
        dbCleanup.close();

    }

    public List<Task>  getAllTask(){
        List<Task> taskList=new ArrayList<>();

        SQLiteDatabase dbCleanup=this.getReadableDatabase();

        Cursor cursor=dbCleanup.rawQuery("SELECT * FROM task",null);
        if (cursor.moveToFirst()){
            do {
             int taskId = cursor.getInt(0);
             int projetId = cursor.getInt(1);
             String taskName = cursor.getString(2);
             int taskDate = cursor.getInt(3);

             Task newTask= new Task(taskId,projetId,taskName,taskDate);
             taskList.add(newTask);
            } while (cursor.moveToNext());

        }
        else
        {}
        cursor.close();
        dbCleanup.close();
        return taskList;

    }

    public Cursor getAllProjet(){
        SQLiteDatabase dbCleanup=this.getReadableDatabase();

        Cursor c=dbCleanup.rawQuery("SELECT * FROM project",null);
        return c;

    }

    public Task getoneTask(int id){
        SQLiteDatabase dbCleanup=this.getReadableDatabase();

        Cursor c=dbCleanup.query("task",new String[] {"_id","projectId","name","creationTimestamp"},"_id=?",
                new String[] {String.valueOf(id)},null,null,null);

        c.moveToFirst();
        Task ts=new Task(c.getInt(0),c.getInt(1),c.getString(2),c.getInt(3));

        return ts;

    }

    public Project getoneProjet(int id){
        SQLiteDatabase dbCleanup=this.getReadableDatabase();

        Cursor c=dbCleanup.query("projet",new String[] {"_id","name","color"},"_id=?",
                new String[] {String.valueOf(id)},null,null,null);

        c.moveToFirst();
        Project pj=new Project(c.getInt(0),c.getString(1),c.getInt(2));

        return pj;

    }

}
