package org.xvolks.jnative.toolkit;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;

public class FileToolkit {
	public static final void selectDirectory(JTextField textField, String message) {
		selectFile(textField, message, false, null);
	}
	/**
	 * Method selectFile
	 *
	 * @param    textField           le textField recevant le fichier
	 * @param    message             le message affiché dans le titre
	 * @param    type                le type de ficher EXT;Libellé par ex : <b>sql;Scripts SQL</b>
	 *
	 * @version  2/21/2006
	 */
	public static final void selectFile(JTextField textField, String message, String type) {
		selectFile(textField, message, true, type);
	}
	
	/**
	 * Method selectFile
	 *
	 * @param    textField           le textField recevant le fichier
	 * @param    message             le message affiché dans le titre
	 * @param	 isFile 			 true = fichier, false = répertoire
	 * @param    type                le type de ficher EXT;Libellé par ex : <b>sql;Scripts SQL</b>
	 *
	 * @version  2/21/2006
	 */
	public static final void selectFile(JTextField textField, final String message, final boolean isFile, String fileType) {
		JFileChooser fc = new JFileChooser(textField.getText());
		fc.setFileHidingEnabled(true);
		fc.setFileSelectionMode(isFile ? JFileChooser.FILES_ONLY : JFileChooser.DIRECTORIES_ONLY);
		if(fileType != null) {
			final String[] params = fileType.split(";");
			fc.setFileFilter(new FileFilter() {
						/**
						 * Whether the given file is accepted by this filter.
						 */
						public boolean accept(File f) {
							return f.isDirectory() || f.getName().endsWith(params[0]);
						}
						
						/**
						 * The description of this filter. For example: "JPG and GIF Images"
						 * @see FileView#getName
						 */
						public String getDescription() {
							return params[1];
						}
					});
		}
		fc.setMultiSelectionEnabled(false);
		fc.setDialogTitle(message);
		int returned = fc.showDialog(textField, "Choisir");
		switch(returned) {
			default:
			case JFileChooser.CANCEL_OPTION:
				break;
			case JFileChooser.APPROVE_OPTION:
				textField.setText(fc.getSelectedFile().getPath());
				break;
		}
	}
}
