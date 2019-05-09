package nl.xelion.gameoflife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import nl.xelion.core.CellBoard;
import nl.xelion.core.Coordinate;
import nl.xelion.core.GameOfLife;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText("");
    }

    @Override
    protected void onResume() {

        super.onResume();

        playXelionGame();
    }

    private void playXelionGame() {

        textView.setText("");

        Coordinate[] seed = new Coordinate[] {new Coordinate(2, 0), new Coordinate(0, 2), new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(3,3)};
        GameOfLife game = new GameOfLife(4, 4, seed);

        int iteration = 0;
        boolean go = true;

        Log.d("GAMEOFLIFE", "Starting layout\n");
        String text = "Starting layout\n";
        CellBoard currentBoard = game.getCurrentBoard();
        Log.d("GAMEOFLIFE", currentBoard != null ? currentBoard.description() : "NULL");
        text += "\n" + currentBoard != null ? currentBoard.description() : "NULL";

        while (iteration < 99 && go) {

            go = game.iterate();
            if (game.isFinished()) {
                go = false;
            }

            Log.d("GAMEOFLIFE", iteration + ". iteration layout\n");
            text += "\n" + iteration + ". iteration layout\n";
            CellBoard _currentBoard = game.getCurrentBoard();
            Log.d("GAMEOFLIFE", _currentBoard != null ? _currentBoard.description() : "NULL");
            text += "\n" + _currentBoard != null ? _currentBoard.description() : "NULL" + "\n";

            iteration++;
        }

        textView.setText(text);
    }
}
